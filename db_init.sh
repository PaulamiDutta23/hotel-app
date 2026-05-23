#!/bin/bash

mongosh "mongodb://localhost:27017/hotel-app" --eval '
  db.hotels.insertMany([
    { name: "Hotel taj", city: "Delhi", availableRooms: 10, pricePerDay:1000.20 },
    { name: "The Oberoi", city: "Mumbai", availableRooms: 15, pricePerDay:1030.20 },
    { name: "Hotel Do Plazza", city: "Bengaluru", availableRooms: 2, pricePerDay:5000.20 },
    { name: "The Diamond", city: "Pune", availableRooms: 40, pricePerDay:3030.20 },
    { name: "ITC Royal Bengal", city: "Hyderabad", availableRooms: 30, pricePerDay:10030.20 }
  ]);