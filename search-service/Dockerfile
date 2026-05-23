FROM denoland/deno:2.3.7 AS builder

WORKDIR /app

COPY deno.json deno.lock* ./

RUN deno cache main.js || true

COPY . .

RUN deno cache main.js

FROM denoland/deno:2.3.7

WORKDIR /app

COPY --from=builder /app /app

EXPOSE 5000

CMD ["deno", "-A",  "main.js"]