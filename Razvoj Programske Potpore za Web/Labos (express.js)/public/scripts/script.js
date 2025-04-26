document.addEventListener("DOMContentLoaded", function () {
  changeTitle();
  fetch("/getCategories")
    .then((response) => response.json())
    .then((categories) => {
      const categoryList = document.getElementById("kategorije");
      categoryList.innerHTML = "";
      categories.forEach((category) => {
        const listItem = document.createElement("li");
        const button = document.createElement("button");
        button.textContent = category;
        button.onclick = () => {
          changeTitle(category);
          fetchProducts(encodeURIComponent(category));
        };
        listItem.appendChild(button);
        categoryList.appendChild(listItem);
      });
    })
    .catch((error) => console.error("Pogreska u dohvatu kategorija:", error));

  fetchCartState();
});

function changeTitle(kategorija) {
  const naslov = document.getElementById("naslov");
  if (kategorija) {
    naslov.textContent = kategorija;
  } else {
    naslov.textContent = "Dobrodošli!";
  }
}

function fetchProducts(category) {
  fetch("/cart/getAll")
    .then((response) => response.json())
    .then((cart) => {
      fetch(`/getProducts/${category}`)
        .then((response) => response.json())
        .then((products) => {
          const productSection = document.getElementById("proizvodi");
          productSection.innerHTML = "";
          products.forEach((product) => {
            const cartItem = cart.find((item) => item.id === product.id);
            const quantity = cartItem ? cartItem.quantity : 0;
            const productDiv = document.createElement("div");
            productDiv.className = "proizvod";
            productDiv.innerHTML = `
              <img src="/${product.image}" alt="${
              product.name
            }" class="proizvod-slika" />
              <div class="proizvod-cart">
                <img src="/images/cart.png" class="proizvod-cart-slika" />
                <p class="proizvod-kolicina" id="kolicina-${product.id}" ${
              quantity > 0 ? "" : 'style="display: none"'
            }>${quantity}</p>
              </div>
              <div class="proizvod-ime">${product.name}</div>
            `;
            productDiv
              .querySelector(".proizvod-cart")
              .addEventListener("click", () => {
                addToCart(product.id);
              });
            productSection.appendChild(productDiv);
          });
        })
        .catch((error) =>
          console.error("Pogreska u dohvatu proizvoda:", error)
        );
    })
    .catch((error) =>
      console.error("Pogreska kod dohvata stanja kosarice", error)
    );
}

function fetchCartState() {
  fetch("/cart/getAll")
    .then((response) => response.json())
    .then((cart) => {
      updateCartCounter(cart);
      updateProductQuantities(cart);
    })
    .catch((error) =>
      console.error("Pogreska kod dohvata stanja kosarice", error)
    );
}

function updateCartCounter(cart) {
  const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
  const cartCounter = document.getElementById("kosarica-broj");
  cartCounter.textContent = totalItems;
  cartCounter.style.display = totalItems > 0 ? "block" : "none";
}

function addToCart(productId) {
  updateCartQuantity(productId, 1);
}

function updateCartQuantity(productId, delta) {
  const url =
    delta > 0 ? `/cart/add/${productId}` : `/cart/remove/${productId}`;
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
      updateCartCounter(cart);
      updateProductQuantities(cart);
    })
    .catch((error) =>
      console.error("Pogreska kod azuriranja kosarice:", error)
    );
}

function updateProductQuantities(cart = []) {
  cart.forEach((item) => {
    const quantityElement = document.getElementById(`kolicina-${item.id}`);
    if (quantityElement) {
      quantityElement.textContent = item.quantity;
      quantityElement.style.display = item.quantity > 0 ? "block" : "none";
    }
  });
}
