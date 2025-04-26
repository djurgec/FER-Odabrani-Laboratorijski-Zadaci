const express = require("express");
const data = require("../data/data");
const router = express.Router();

router.get("/", function (req, res) {
  res.render("home", {
    categories: data.categories.map((category) => category.name),
    products: [],
  });
});

router.get("/getCategories", function (req, res) {
  const categoryNames = data.categories.map((category) => category.name);
  res.json(categoryNames);
});

router.get("/getProducts/:categoryName", function (req, res) {
  const categoryName = decodeURIComponent(req.params.categoryName);
  const category = data.categories.find((cat) => cat.name === categoryName);
  if (category) {
    res.json(category.products);
  } else {
    res.status(404).send({ error: "Kategorija nije pronađena" });
  }
});

module.exports = router;
