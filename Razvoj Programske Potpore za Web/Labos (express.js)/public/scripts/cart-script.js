document.addEventListener("DOMContentLoaded", () => {
  fetchCartState();

  document.getElementById("retci").addEventListener("click", (event) => {
    const target = event.target;
    const index = parseInt(target.dataset.index);
    const productId = target.dataset.productId;

    if (target.classList.contains("smanji")) {
      const currentQuantity = parseInt(target.nextElementSibling.textContent);
      if (currentQuantity > 1) {
        updateCartQuantity(productId, -1);
      } else {
        removeProductFromCart(productId);
      }
    } else if (target.classList.contains("povecaj")) {
      updateCartQuantity(productId, 1);
    }
  });
});

function fetchCartState() {
  fetch("/cart/getAll")
    .then((response) => response.json())
    .then((cart) => {
      localStorage.setItem("cartArray", JSON.stringify(cart));
      displayCart(cart);
      updateCartCounter(cart);
    })
    .catch((error) => console.error("Error fetching cart state:", error));
}

function updateCartQuantity(productId, delta) {
  const url =
    delta > 0 ? `/cart/add/${productId}` : `/cart/subtract/${productId}`;
  fetch(url, {
    method: "POST",
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw new Error("Pogreska kod azuriranja kosarice");
    })
    .then((cart) => {
      localStorage.setItem("cartArray", JSON.stringify(cart));
      displayCart(cart);
      updateCartCounter(cart);
    })
    .catch((error) =>
      console.error("Pogreska kod azuriranja kosarice:", error)
    );
}

function removeProductFromCart(productId) {
  fetch(`/cart/remove/${productId}`, {
    method: "POST",
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw new Error("Pogreska kod azuriranja kosarice");
    })
    .then((cart) => {
      localStorage.setItem("cartArray", JSON.stringify(cart));
      displayCart(cart);
      updateCartCounter(cart);
    })
    .catch((error) =>
      console.error("Pogreska kod azuriranja kosarice:", error)
    );
}

function displayCart(cart) {
  const cartContainer = document.getElementById("retci");
  cartContainer.innerHTML = "";

  cart.forEach((item, index) => {
    const cartItem = document.createElement("div");
    cartItem.classList.add("redak");
    cartItem.innerHTML = `
      <p id="naziv">${item.productName}</p>
      <div id="kolicina-redak">
        <button class="smanji" data-index="${index}" data-product-id="${item.id}">-</button>
        <p id="kolicina">${item.quantity}</p>
        <button class="povecaj" data-index="${index}" data-product-id="${item.id}">+</button>
      </div>
    `;
    cartContainer.appendChild(cartItem);
  });
}

function updateCartCounter(cart) {
  const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
  const cartCounter = document.getElementById("cart-kosarica-broj");
  cartCounter.textContent = totalItems;
  cartCounter.style.display = totalItems > 0 ? "block" : "none";
}
