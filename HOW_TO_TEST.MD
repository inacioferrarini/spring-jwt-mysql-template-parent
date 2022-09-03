# About

How to test and validate the features. Some scenarios may not apply.

# Integration Test Scenarios

Integration tests goes from request to database and validate that the request data was properly stored on the database.

## Find All

### Success Scenarios

#### Having Data to Return

| HTTP Status | Body               |
|-------------|--------------------|
| 200 (OK)    | [{ ... }, { ... }] |

#### No Data to Return

| HTTP Status | Body |
|-------------|------|
| 200 (OK)    | []   |

### Failure Scenarios

#### Invalid Access Token

| HTTP Status        | Body |
|--------------------|------|
| 401 (Unauthorized) | None |

## Find By Id

### Success Scenarios

#### Having Data to Return

| HTTP Status | Body    |
|-------------|---------|
| 200 (OK)    | { ... } |

### Failure Scenarios

#### Invalid Access Token

| HTTP Status        | Body |
|--------------------|------|
| 401 (Unauthorized) | None |

#### Not Found

| HTTP Status     | Body |
|-----------------|------|
| 404 (Not Found) | None |

#### Not the Owner

| HTTP Status     | Body |
|-----------------|------|
| 404 (Not Found) | None |

## Create

### Success Scenarios

#### Create

| HTTP Status   | Body    |
|---------------|---------|
| 201 (Created) | { ... } |

### Failure Scenarios

#### Invalid Access Token

| HTTP Status        | Body |
|--------------------|------|
| 401 (Unauthorized) | None |

#### Validation Error

| HTTP Status                 | Body          |
|-----------------------------|---------------|
| 422 (Unprocessable Content) | Error Message |

#### Duplicated

| HTTP Status    | Body          |
|----------------|---------------|
| 409 (Conflict) | Error Message |

## Update

### Success Scenarios

#### Update

| HTTP Status | Body    |
|-------------|---------|
| 200 (OK)    | { ... } |

### Failure Scenarios

#### Invalid Access Token

| HTTP Status        | Body |
|--------------------|------|
| 401 (Unauthorized) | None |

#### Validation Error

| HTTP Status                 | Body          |
|-----------------------------|---------------|
| 422 (Unprocessable Content) | Error Message |

#### Not Found

| HTTP Status     | Body |
|-----------------|------|
| 404 (Not Found) | None |

#### Not the Owner

| HTTP Status     | Body |
|-----------------|------|
| 404 (Not Found) | None |

#### Duplicated

| HTTP Status    | Body          |
|----------------|---------------|
| 409 (Conflict) | Error Message |

## Delete

### Success Scenarios

#### Delete

| HTTP Status      | Body |
|------------------|------|
| 204 (No Content) | None |

### Failure Scenarios

#### Invalid Access Token

| HTTP Status        | Body |
|--------------------|------|
| 401 (Unauthorized) | None |

#### Not Found

| HTTP Status     | Body |
|-----------------|------|
| 404 (Not Found) | None |

#### Not the Owner

| HTTP Status     | Body |
|-----------------|------|
| 404 (Not Found) | None |
