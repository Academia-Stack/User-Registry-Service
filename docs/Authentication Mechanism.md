# JSON Web Tokens (JWTs)

A JSON Web Token (JWT) is a compact, URL-safe means of representing claims, that needs to be transferred between two parties. The claims in a JWT are encoded as a JSON object that is digitally signed using a JSON Web Signature (JWS) or encrypted using a JSON Web Encryption (JWE).

## Structure of a JWT
A JWT consists of three parts separated by dots (`.`), which are:

1.  **Header**
2.  **Payload**
3.  **Signature**

It typically looks like this:
`xxxxx.yyyyy.zzzzz`

### 1. Header

The header typically consists of two parts: the type of the token (JWT) and the signing algorithm being used (e.g., HMAC SHA256 or RSA). This is a JSON object that is Base64Url-encoded.

**Example Header:**
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

*   `"alg"`: Specifies the algorithm used for signing the token. Common values are `HS256` (HMAC with SHA-256) and `RS256` (RSA with SHA-256).
*   `"typ"`: Indicates the type of the token, which is `JWT`.

### 2. Payload (Claims)

The payload contains the claims. Claims are statements about an entity (typically, the user) and additional data. There are three types of claims:

*   **Registered Claims**: These are a set of predefined claims which are not mandatory but recommended to provide a set of useful, interoperable claims. Examples include: `iss` (issuer), `exp` (expiration time), `sub` (subject), `aud` (audience).
*   **Public Claims**: These can be defined by those using JWTs. To avoid collisions, they should be defined in the IANA JSON Web Token Registry or be defined as a URI that contains a collision-resistant namespace.
*   **Private Claims**: These are custom claims created to share information between parties that agree on using them. They are not registered or public and should be used with caution as they can conflict.

**Example Payload:**
```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "iat": 1516239022
}
```

*   `"sub"`: Subject of the JWT (e.g., user ID).
*   `"name"`: User's name.
*   `"iat"`: "Issued at" time, indicating when the JWT was issued, as a Unix timestamp.

This JSON object is also Base64Url-encoded.

### 3. Signature

The signature is used to verify that the sender of the JWT is who it says it is and to ensure that the message hasn't been altered along the way. To create the signature, you take the encoded header, the encoded payload, a secret, and the algorithm specified in the header, and sign that. For example, if you are using the HS256 algorithm, the signature is created as follows:

```
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret
)
```

*   **`base64UrlEncode(header)`**: The Base64Url-encoded header string.
*   **`base64UrlEncode(payload)`**: The Base64Url-encoded payload string.
*   **`secret`**: A secret key known only to the issuer and the server to verify the token.

## How JWT Works as a System

1.  **Authentication Request**: A user logs in with their credentials (e.g., username and password) to an authentication server.

2.  **JWT Issuance**: If the credentials are valid, the authentication server creates a JWT. It constructs the header and payload, Base64Url-encodes them, and then signs them with a secret key to create the signature. The complete JWT (header.payload.signature) is then sent back to the client.

3.  **Client Storage**: The client (e.g., a web browser or mobile app) typically stores this JWT in local storage or a cookie.

4.  **Resource Request**: When the client needs to access a protected resource (e.g., an API endpoint), it sends the JWT, usually in the `Authorization` header as a `Bearer` token.

    **JWTs as Bearer Tokens**: JWTs are commonly used as 'bearer tokens'. This means that the holder ('bearer') of the token is granted access to the resource based on possessing the token. The client typically sends the JWT in the `Authorization` header as `Authorization: Bearer <token>`.

5.  **Token Verification**: The resource server receives the JWT. It separates the header, payload, and signature. Using the same algorithm specified in the header and the same secret key, it recomputes the signature based on the received header and payload. It then compares this newly computed signature with the signature received in the token.

6.  **Authorization**: If the signatures match, the token is considered valid and untampered. The server then decodes the payload to extract the claims (e.g., user ID, roles, permissions) and determines if the user has the necessary authorization to access the requested resource. If valid and authorized, the server returns the requested resource.

7.  **No Server-Side State**: A key advantage of JWTs is that they are stateless. The server doesn't need to store session information. All the necessary information for authentication and authorization is contained within the token itself.

### Advantages of JWTs

*   **Compact**: Due to their small size, JWTs can be sent through URL, POST parameter, or inside an HTTP header.
*   **Self-contained**: The payload contains all the necessary user information, reducing the need for the server to query a database for user details on every request.
*   **Stateless**: Eliminates the need for session management on the server, simplifying scalability across multiple servers.
*   **Security**: Signed JWTs verify the integrity of the claims and prevent tampering. Encrypted JWTs provide confidentiality.

### Disadvantages of JWTs

*   **Token Invalidation**: Once issued, a JWT is valid until its expiration time. There's no easy way to revoke a JWT if a user logs out or if the token is compromised, without implementing additional mechanisms (like blacklists).
*   **Payload Size**: While compact, if too much information is put into the payload, the token can become large, impacting performance.
*   **Security of Secret Key**: If the secret key used to sign the token is compromised, an attacker can forge valid tokens.
***