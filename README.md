Jeremiah Ekanem










this is a multi-module springboot application that allows users to login, create other users all while under strict rules of authorization and authentication


these are the endpoints and sample responses you can use to do certain things.
(1) there is a default user already seeded into the system for security purposes
http://localhost:8080/api/login          ----------> Get method
this is the credential
{
    "username" : "superadmin",
    "password" : "SuperAdmin@123"
}
this is the sample response
{
	"success": true,
	"message": "Request successful",
	"data": {
		"success": true,
		"message": "Login successful",
		"data": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiLCJST0xFX0FETUlOIiwiUk9MRV9BVURJVE9SIl0sInVzZXJJZCI6MSwidXNlcm5hbWUiOiJzdXBlcmFkbWluIiwic3ViIjoic3VwZXJhZG1pbiIsImlhdCI6MTc3MDgwNDYxNCwiZXhwIjoxNzcwODA4MjE0fQ.b72UVbzOC17XIXNx-fp3grQXKGqpxjCbRfnM-6iqtTM",
		"status": 200,
		"errorCode": null,
		"timestamp": "2026-02-11T10:10:15.055545Z",
		"path": "/api/login"
	},
	"status": 200,
	"errorCode": null,
	"timestamp": "2026-02-11T10:10:15.099551Z",
	"path": "/api/login"
}

(2) using the token gotten from one(1) above, you can use it to view details about you
http://localhost:8080/api/user/me      ----------> Get method
{
	"success": true,
	"message": "Request successful",
	"data": {
		"success": true,
		"message": "About me",
		"data": {
			"createdAt": "2026-02-11T03:21:51.121428",
			"enabled": true,
			"id": 1,
			"password": "$2a$10$/1mZ65eu9R446.yFbbSksOITU.SUNDfk2Us4XXi1Iy8GOrWG2K2OK",
			"roles": [
				"ROLE_ADMIN",
				"ROLE_AUDITOR",
				"ROLE_USER"
			],
			"updatedAt": "2026-02-11T03:21:51.121428",
			"username": "superadmin"
		},
		"status": 200,
		"errorCode": null,
		"timestamp": "2026-02-11T09:51:40.157133Z",
		"path": "/api/user/me"
	},
	"status": 200,
	"errorCode": null,
	"timestamp": "2026-02-11T09:51:40.229740Z",
	"path": "/api/user/me"
}



(3) You can view a public health endpoint without token
http://localhost:8080/api/public/health        -----------> Get method
this is a sample response
{
	"success": true,
	"message": "Request successful",
	"data": {
		"success": true,
		"message": "Service is up",
		"data": "OK",
		"status": 200,
		"errorCode": null,
		"timestamp": "2026-02-11T09:10:55.085280Z",
		"path": "/api/public/health"
	},
	"status": 200,
	"errorCode": null,
	"timestamp": "2026-02-11T09:10:55.127440Z",
	"path": "/api/public/health"
}



(4) you can view a paginated list of users. you will need to use a token with this and have the role of ADMIN
http://localhost:8080/api/admin/users            --------------> Get method
this is the sample response
{
	"success": true,
	"message": "Request successful",
	"data": {
		"success": true,
		"message": "Users fetched successfully",
		"data": {
			"content": [
				{
					"createdAt": "2026-02-11T03:21:51.121428",
					"enabled": true,
					"id": 1,
					"password": "$2a$10$/1mZ65eu9R446.yFbbSksOITU.SUNDfk2Us4XXi1Iy8GOrWG2K2OK",
					"roles": [
						"ROLE_USER",
						"ROLE_ADMIN",
						"ROLE_AUDITOR"
					],
					"updatedAt": "2026-02-11T03:21:51.121428",
					"username": "superadmin"
				},
				{
					"createdAt": "2026-02-11T11:10:34.83366",
					"enabled": true,
					"id": 2,
					"password": "$2a$10$sSwvml.uTLEAWT5XV.pQMOrTuhl7AslK1dwFFKU8pIYMP4uMnFAM6",
					"roles": [
						"ROLE_USER"
					],
					"updatedAt": "2026-02-11T11:10:34.83366",
					"username": "john"
				}
			],
			"empty": false,
			"first": true,
			"last": true,
			"number": 0,
			"numberOfElements": 2,
			"pageable": {
				"offset": 0,
				"pageNumber": 0,
				"pageSize": 20,
				"paged": true,
				"sort": {
					"empty": true,
					"sorted": false,
					"unsorted": true
				},
				"unpaged": false
			},
			"size": 20,
			"sort": {
				"empty": true,
				"sorted": false,
				"unsorted": true
			},
			"totalElements": 2,
			"totalPages": 1
		},
		"status": 200,
		"errorCode": null,
		"timestamp": "2026-02-11T10:10:57.702253Z",
		"path": "/api/admin/users"
	},
	"status": 200,
	"errorCode": null,
	"timestamp": "2026-02-11T10:10:57.706729Z",
	"path": "/api/admin/users"
}



(5) Lastly you can add users. you will need a token for this
http://localhost:8080/api/create/user        --------------> POST method
{
  "username": "john",
  "password": "password123",
  "roles": ["ROLE_USER"],
  "enabled": true
}
this is a sample response
{
	"success": true,
	"message": "Request successful",
	"data": {
		"success": true,
		"message": "User created successfully",
		"data": {
			"createdAt": "2026-02-11T11:10:34.83366",
			"enabled": true,
			"id": 2,
			"password": "$2a$10$sSwvml.uTLEAWT5XV.pQMOrTuhl7AslK1dwFFKU8pIYMP4uMnFAM6",
			"roles": [
				"ROLE_USER"
			],
			"updatedAt": "2026-02-11T11:10:34.83366",
			"username": "john"
		},
		"status": 201,
		"errorCode": null,
		"timestamp": "2026-02-11T10:10:34.907816Z",
		"path": "/api/create/user"
	},
	"status": 201,
	"errorCode": null,
	"timestamp": "2026-02-11T10:10:34.912628Z",
	"path": "/api/create/user"
}





