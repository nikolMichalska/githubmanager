<h1>Github manager</h1>
<br>
The goal of the Github manager application is to list all user's github repositories with:
<li>repository name</li>
<li>user's login</li>
<li>branch name</li>
<li>last commit sha</li>

<h2>Tech Stack</h2>
<p><b>Language: </b> Java 17
<p><b>Framework: </b> Spring Boot 3.1.3
<p><b>Build tool: </b> Maven
<p><b>API: </b> GitHub API
<p><b>UI: </b> Rest API

<h2>API reference</h2>
Get all repositories which are not forks with repository name, user's login, branch name and last commit sha

```text
"/get-all-repositories/{userName}"
```

<h4>Example of the response in JSON format: </h4>

```json
{
"name": "diceGameJS",
"login": "nikolMichalska",
"branches": [
{
"name": "main",
"commitSha": "f7a51652f439a92bbf0a7cc544f2a7f75c603d15"
}
]
}
```

<h2>Errors</h2>
2 errors have been handled in the application:
<h4>1. When GitHub user does not exist, you will receive: </h4>
```json
{
    "status": 404,
    "message": "User not found"
}
```

<br>
<h4>2. Given application/xml Accept header, you will get:  </h4>

```json
{
  "status": 406,
  "message": "Not acceptable accept header"
}
```

