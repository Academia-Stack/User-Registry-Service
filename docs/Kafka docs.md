# Kafka documentation
## JSON Schema for LogEntry object
This schema is used in the topics for validating messages
```json
{
  "type": "object",
  "title": "LogEntry",
  "properties": {
    "logId": {
      "type": "string",
      "format": "uuid"
    },
    "createdDate": {
      "type": "string",
      "format": "date"
    },
    "createdTime": {
      "type": "string",
      "format": "time"
    },
    "message": {
      "type": "string"
    },
    "endPoint": {
      "type": "string"
    },
    "method": {
      "type": "string"
    },
    "exceptionClass": {
      "type": "string"
    }
  },
  "required": [
    "logId",
    "message"
  ]
}
```
***
