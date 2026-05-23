import {Hono} from "hono";
import {logger} from "hono/logger";
import {bookHandler, searchHandler} from "./handlers.js";

export const createApp = (redisClient, hotels) => {
  const app = new Hono();
  app.use((c, next) => {
    c.set("redisClient", redisClient);
    c.set("hotels", hotels);
    return next();
  })
  app.use(logger());

  app.get("api/search/hotels", searchHandler);

  app.post("internal/api/hotel/book", bookHandler)

  return app;
}