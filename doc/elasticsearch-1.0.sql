#高新问答表
curl -XPUT "http://localhost:9200/high_tech_questions" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "table":{
      "properties":{
        "question":{
          "type":"string",
          "analyzer":"ik_max_word"
        },
        "answer":{
          "type":"string",
          "analyzer":"ik_max_word"
        },
        "addtime":{
          "type":"date",
          "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
        },
        "updatedTime":{
          "type":"date",
          "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
        }
      }
    }
  }
}'