# Spring Spoonacular proxy

The Angular app calls your backend under `http://localhost:8080/api/spoonacular/...`. The proxy must forward **every** query parameter the browser sends to Spoonacular, then attach the **server-side** `apiKey` from `application.properties` (`spoonacular.api.key`).

## Pagination (`complexSearch`)

Spoonacular uses `number` (page size) and `offset` (skip). If the proxy only binds `query` with `@RequestParam String query`, **`offset` and `number` are dropped** and “load more” keeps returning the first page.

**Do:** build the outbound URL from the incoming request parameters (or from `request.getQueryString()` after stripping any `apiKey`), then add `apiKey` once on the server.

**Don’t:**

```java
@GetMapping("/recipes/complexSearch")
public ResponseEntity<String> search(@RequestParam String query) { ... }
```

## Reference implementation

See `SpoonacularController`:

- `GET /api/spoonacular/recipes/complexSearch` and `GET /api/spoonacular/complexSearch` copy all parameters except `apiKey`, then append the configured key.
- `GET /api/spoonacular/recipes/{id}/information` (and legacy `/{id}/information`) forward query params the same way; if `includeNutrition` is omitted, it defaults to `false`.

## Optional local dev (no Spring)

Point the app at Spoonacular directly (dev only): `spoonacularBaseUrl` = `https://api.spoonacular.com/recipes/`, `spoonacularApiKey` = your key. Do not ship the key in production frontends.
