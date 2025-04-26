const express = require("express");
const session = require("express-session");
const path = require("path");
const app = express();
const homeRoute = require("./routes/homeRoute");
const cartRoute = require("./routes/cartRoute");

app.use(express.urlencoded({ extended: false }));
app.use(express.json());

app.use(
  session({
    secret: "anything",
    resave: false,
    saveUninitialized: true,
  })
);

app.set("views", path.join(__dirname, "views"));
app.set("view engine", "ejs");

app.use(express.static(path.join(__dirname, "public")));

app.use("/", homeRoute);
app.use("/cart", cartRoute);
app.listen(3000);
