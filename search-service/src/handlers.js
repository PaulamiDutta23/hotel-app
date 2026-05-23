import {ObjectId} from "mongodb";

export const searchHandler = async (c) => {
    const city = c.req.query("city");
    const hotels = c.get('hotels');
    const redisClient = c.get("redisClient");
    const cache = await redisClient.get(`city:${city}`);

    if (cache) {
        const result = JSON.parse(cache);
        return c.json(result);
    }

    const hotelResults = await hotels.find({city}).toArray();
    await redisClient.set(`city:${city}`, JSON.stringify(hotelResults), {ex: 10});
    return c.json(hotelResults);
};

export const bookHandler = async (c) => {
    const body = await c.req.json();
    const hotels = c.get("hotels");
    const hotel = await hotels.findOne({_id: new ObjectId(body.hotelId)});

    if (!hotel) return c.json("Hotel not found", 400);
    const availableRooms = hotel.availableRooms;

    if (availableRooms < body.totalRooms)
        return c.json("Insufficient rooms available", 400);

    hotels.updateOne({_id: new ObjectId(body.hotelId)}, {$set: {availableRooms: availableRooms - body.totalRooms}});

    return c.json(await hotels.findOne({_id: new ObjectId(body.hotelId)}));
}