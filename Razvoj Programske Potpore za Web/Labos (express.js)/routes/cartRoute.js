const express = require("express");
const router = express.Router();
const data = require("../data/data");

function initializeCart(req) {
  if (!req.session.cart) {
    req.session.cart = [];
  }
}

function findProductById(id) {
  for (const category of data.categories) {
    for (const product of category.products) {
      if (product.id === id) {
        return product;
      }
    }
  }
  return null;
}

router.post("/add/:id", (req, res) => {
  initializeCart(req);

  const productId = parseInt(req.params.id, 10);
  const product = findProductById(productId);

  if (product) {
    const existingItem = req.session.cart.find((item) => item.id === productId);
    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      req.session.cart.push({
        id: productId,
        productName: product.name,
        quantity: 1,
      });
    }
    res.status(200).json(req.session.cart);
  } else {
    res.status(404).json({ error: "Proizvod nije pronađen." });
  }
});

router.post("/subtract/:id", (req, res) => {
  initializeCart(req);

  const productId = parseInt(req.params.id, 10);
  const product = findProductById(productId);

  if (product) {
    const existingItem = req.session.cart.find((item) => item.id === productId);
    if (existingItem) {
      existingItem.quantity -= 1;
    } else {
      req.session.cart.push({
        id: productId,
        productName: product.name,
        quantity: 1,
      });
    }
    res.status(200).json(req.session.cart);
  } else {
    res.status(404).json({ error: "Proizvod nije pronađen." });
  }
});

router.post("/remove/:id", (req, res) => {
  initializeCart(req);

  const productId = parseInt(req.params.id, 10);
  const itemIndex = req.session.cart.findIndex((item) => item.id === productId);

  if (itemIndex > -1) {
    req.session.cart.splice(itemIndex, 1);
    res.status(200).json(req.session.cart);
  } else {
    res.status(404).json({ error: "Proizvod nije pronađen u košarici." });
  }
});

router.get("/getAll", (req, res) => {
  initializeCart(req);
  res.json(req.session.cart);
});

router.get("/", (req, res) => {
  res.render("cart");
});

module.exports = router;
