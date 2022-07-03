# ElasticSearch基本API操作

## 索引

```bash
# 查看es中的索引
GET /_cat/indices
GET /_cat/indices?v

# 创建索引
PUT /products

# 创建索引 进行索引分片配置
PUT /orders
{
  "settings": {
    "number_of_shards": 1, # 指定主分片的数量
    "number_of_replicas": 0 # 指定副本分片的数量
  }
}

# 删除索引
DELETE /orders

# 没有修改操作
```

## 映射

```bash
# 创建索引和映射
PUT /products
{ 
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0
  }, 
  "mappings": {
    "properties": {
      "title":{
        "type": "keyword"
      },
      "price":{
        "type": "double"
      },
      "created_at":{
        "type": "date"
      },
      "description":{
        "type": "text"
      }
    }
}
 
# 查看某个索引的映射，映射不能修改和删除
GET /products/_mapping
```

## 文档

```bash
# 添加文档，可以指定id，不指定的话自动生成
POST /products/_doc/1 
{
  "title":"iphone13",
  "price":8999.99,
  "created_at":"2021-09-15",
  "description":"iPhone 13屏幕采用6.1英寸OLED屏幕。"
}

# 文档查询，基于id查询
GET /products/_doc/1

# 文档删除，基于Id
DELETE /products/_doc/1

# 更新文档，删除原始文档，再重新添加
PUT /products/_doc/1
{
  "title":"iphon15"
}

# 更新文档某个字段
POST /products/_doc/1/_update
{
    "doc" : {
        "title" : "iphon15"
    }
}
```

## 高级查询

```bash
# 查询所有
GET /products/_search
{
  "query":{
    "match_all":{
      
    }
  }
}

# 关键词查询
# 如果搜索字段是keyword，要用全部内容搜索，keyword，int，double，date不分词
# text类型，默认es标准分词器，中文单字分词，英文单词分词
# 除了text分词，其他的不分词
# 使用term查询，是使用es默认的分词器
GET /products/_search
{
  "query": {
    "term": {
      "description": {
        "value": "iPhone"
      }
    }
  }
}

# 范围查询 ranger
GET /products/_search
{
  "query": {
    "range": {
      "price": {
        "gte": 0,
        "lte": 20000
      }
    }
  }
}

# 前缀查询
GET /products/_search
{
  "query": {
    "prefix": {
      "title": {
        "value": "iph"
      }
    }
  }
}

# 通配符查询 ?匹配一个，*匹配多个
GET /products/_search
{
  "query": {
    "wildcard": {
      "description": {
        "value": "iPhone"
      }
    }
  }
}

# ids 查询，查询一组符合条件的id
GET /products/_search
{
  "query": {
    "ids": {
      "values": ["GjIKv4EBDQApVgZTHDTS","GzIKv4EBDQApVgZTHDTS"]
    }
  }
}

# bool 查询
GET /products/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "ids": {
            "values": ["GjIKv4EBDQApVgZTHDTS"]
          }
        },{
          "term": {
            "title": {
              "value": "iphone12"
            }
          }
        }
      ]
    }
  }
}

# multi_match 多字段查询
# query 可以输入关键词，也可以输入一段文本
GET /products/_search
{
  "query": {
    "multi_match": {
      "query": "iphone",
      "fields": ["title","description"]
    }
  }
}

# query_string
GET /products/_search
{
  "query": {
    "query_string": {
      "default_field": "description",
      "query": "四种配色"
    }
  }
}

# 高亮
GET /products/_search
{
  "query": {
    "query_string": {
      "default_field": "description",
      "query": "四种配色"
    }
  },
  "highlight": {
    "fields": {
      "description": {}
    }
  }
}

# 分页
GET /products/_search
{
  "query": {
    "query_string": {
      "default_field": "description",
      "query": "四种配色"
    }
  },
  "from": 0,
  "size": 20
}

# 排序
GET /products/_search
{
  "query": {
    "query_string": {
      "default_field": "description",
      "query": "四种配色"
    }
  },
  "sort": [
    {
      "price": {
        "order": "desc"
      }
    }
  ]
}

# 返回字段
GET /products/_search
{
  "query": {
    "query_string": {
      "default_field": "description",
      "query": "四种配色"
    }
  },
  "_source": ["title","price"]
}
```

