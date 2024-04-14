# java-explore-with-me
![project.png](stats-service%2Fstats-server%2Fsrc%2Fmain%2Fresources%2Fstatic%2Fproject.png)
ExploreWithMe project.

Stats-service.
Hits:
- POST /hit - add statistic hit
- GET /stats - get statistic

Ewm-service.

Users:
- POST /admin/users - add user
- GET /admin/users - get users list from page users by list ids
- DELETE /admin/users/{userId} - delete user by id

Categories:
- POST /admin/categories - add category
- GET /categories/{catId} - get category by id
- GET /categories - get categories list from page
- PATCH /admin/categories/{catId} - patch category
- DELETE /admin/categories/{catId} - delete category

Events:
- POST /users/{userId}/events - add event
- GET /users/{userId}/events - get list events from page by user id
- GET /admin/events - get list events by selection parameters
- GET /users/{userId}/events/{eventId} - get event by user id and event id
- GET /users/{userId}/events/{eventId}/requests - get list requests on event by user id
- GET /events - get list events from page by selection parameters
- GET /events/{id} - get event by id, public only
- PATCH /admin/events/{eventId} - update time or status event by id
- PATCH /users/{userId}/events/{eventId} - update event
- PATCH /users/{userId}/events/{eventId}/requests - change status request by owner event

Compilations:
- POST /admin/compilations - add compilation
- GET /compilations - get list compilation from page
- GET /compilations/{compId} - get compilation by id
- PATCH /admin/compilations/{compId} - update compilation
- DELETE /admin/compilations/{compId} - delete compilation

Requests:
- POST users/{userId}/requests - add request
- GET users/{userId}/requests - get list requests by owner id
- PATCH users/{userId}/requests/{requestId}/cancel - update status request on canceled

Comments:
- POST /users/{userId}/events/{eventId}/comments - add comment
- POST /users/{userId}/events/{eventId}/comments/{commentId} - add attached comment on comment id
- GET /users/{userId}/events/{eventId}/comments/{commentId} - get comment by id
- GET /users/{userId}/events/{eventId}/comments - get list comments by event id
- PATCH /users/{userId}/events/{eventId}/comments/{commentId} - update comment by id
- PATCH /admin/comments/{commentId} - update comment by admin, banned by admin
- DELETE /admin/comments/{commentId} - delete comment by admin

https://github.com/MaXM91/java-explore-with-me/pull/5#issue-2237712689
