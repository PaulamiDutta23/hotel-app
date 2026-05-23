import {MongoClient} from "mongodb";
import {connect} from "@db/redis";
import {createApp} from "./src/app.js";

const main = async () => {
  const mongoClient = new MongoClient(Deno.env.get("HOTEL_MONGO_URI") || "mongodb://localhost:27017");
  const hotelDb = mongoClient.db("hotel-app");
  const hotels = hotelDb.collection("hotels");
  const redisClient = await connect({ hostname: Deno.env.get("REDIS_HOST") || "127.0.0.1", port:6379});
  const app = createApp(redisClient, hotels);
  Deno.serve({ port: 5000 }, app.fetch);
}

await main();