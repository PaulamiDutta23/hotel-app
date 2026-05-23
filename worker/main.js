import {connect} from "@db/redis";
import {generateReceipt} from "./receipt_generator.js";

const connectToDb = async () => {
  const hostname = Deno.env.get("REDIS_HOST") || "127.0.0.1";
  return await connect({
    hostname,
    port: 6379,
  });
};

const startWorker = async (redis) => {
  console.log("Worker started... waiting for jobs");

  while (true) {
    try {
      const result = await redis.brpop(0, "receiptJobs");

      const [queueName, booking] = result;
      const job = JSON.parse(booking);

      console.log(queueName, job);
      await generateReceipt(job);
    } catch (err) {
      console.error("Worker error:", err);
    }
  }
};

const main = async () => {
  console.log("connecting...");
  const redis = await connectToDb();
  console.log("connected...");

  startWorker(redis);
};

main();
