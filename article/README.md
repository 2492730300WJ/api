# article 文章服务

---
# 设计思路

## 文章详情
 - RedisKey
   ```
    article_a_b  a:文章ID b:作者ID
   ```
 - RedisValue
    ```
   {
        "title": "文章标题",
        "content": "文章内容",
        "star": 1,
        "fork": 1，
        "watch":1,
        "articleId":1,
        "createTime":"2021-01-01 11:11:11",
        "updateTime":"2021-01-01 12:12:12"
    }
   ```
 - Description
    ``` 
    当用户查询某文章时，先去redis中查询是否有该文章的详情信息，如果有直接查询，如果没有查询mysql；
    查完数据库后，将文章详情存入redis，设置有效期为一天，一天内有用户再次访问该文章，直接读取redis。
    ```

## 文章列表

## 点赞（star） && 分享（fork）
 - RedisKey
   ```
    article_star_a  a:文章ID  bitmap
    article_fork_a  a:文章ID  set
   ```
 - RedisValue
   ```
   star_b b:点赞人id
   fork_b b:点赞人id
   ```    

 - Description
    ```
   存入redis的z-set，按天去重，保存成功后更改对应数量，之后通过定时服务中的任务每隔一小时存入mysql。
    ```
    
