const data = {
  website: "Hrana",
  categories: [
    {
      name: "Voće",
      products: [
        {
          id: 1,
          name: "Jabuke, 1kg",
          image: "images/fruit/jabuke.jpg",
        },
        {
          id: 2,
          name: "Banane, 1kg",
          image: "images/fruit/banane.png",
        },
        {
          id: 3,
          name: "Jagode, 1kg",
          image: "images/fruit/jagode.jpg",
        },
        {
          id: 4,
          name: "Kruške, 1kg",
          image: "images/fruit/kruske.png",
        },
        {
          id: 5,
          name: "Naranče, 1kg",
          image: "images/fruit/narance.jpg",
        },
      ],
    },
    {
      name: "Povrće",
      products: [
        {
          id: 6,
          name: "Krumpir, 1kg",
          image: "images/vegetables/krumpir.png",
        },
        {
          id: 7,
          name: "Luk, 1kg",
          image: "images/vegetables/luk.png",
        },
        {
          id: 8,
          name: "Paprika, 1kg",
          image: "images/vegetables/paprika.png",
        },
        {
          id: 9,
          name: "Rajčica, 1 kg",
          image: "images/vegetables/rajcica.png",
        },
        {
          id: 10,
          name: "Zelena salata, 1kg",
          image: "images/vegetables/salata.png",
        },
      ],
    },
    {
      name: "Meso",
      products: [
        {
          id: 11,
          name: "Kobasice, 500g",
          image: "images/meat/kobasice.png",
        },
        {
          id: 12,
          name: "Pileće mljeveno meso, 750g",
          image: "images/meat/mljeveno_meso.png",
        },
        {
          id: 13,
          name: "Pileća prsa, 500g",
          image: "images/meat/pileca_prsa.png",
        },
        {
          id: 14,
          name: "Pileći batci, 1kg",
          image: "images/meat/pileci_batak.png",
        },
        {
          id: 15,
          name: "Juneći ramstek, 250g",
          image: "images/meat/ramstek.png",
        },
      ],
    },
    {
      name: "Riba",
      products: [
        {
          id: 16,
          name: "Losos, 200g",
          image: "images/fish/losos.png",
        },
        {
          id: 17,
          name: "Oslić, 400g",
          image: "images/fish/oslic.png",
        },
        {
          id: 18,
          name: "Škampi, 250g",
          image: "images/fish/skampi.png",
        },
        {
          id: 19,
          name: "Sushi, 200g",
          image: "images/fish/sushi.png",
        },
        {
          id: 20,
          name: "Tuna u konzervi, 150g",
          image: "images/fish/tuna.png",
        },
      ],
    },
    {
      name: "Kruh",
      products: [
        {
          id: 21,
          name: "Kruh baguette, 500g",
          image: "images/bread/baguette.png",
        },
        {
          id: 22,
          name: "Kruh od banane, 750g",
          image: "images/bread/banana_kruh.png",
        },
        {
          id: 23,
          name: "Polubijeli kruh, 750g",
          image: "images/bread/polubijeli_kruh.png",
        },
        {
          id: 24,
          name: "Tamni kruh, 500g",
          image: "images/bread/tamni_kruh.png",
        },
        {
          id: 25,
          name: "Tost, 250g",
          image: "images/bread/tost.png",
        },
      ],
    },
    {
      name: "Žitarice",
      products: [
        {
          id: 26,
          name: "Čokoladne žitarice, 200g",
          image: "images/cereal/cokoladne_zitarice.png",
        },
        {
          id: 27,
          name: "Granola, 250g",
          image: "images/cereal/granola.png",
        },
        {
          id: 28,
          name: "Kukuruzne žitarice, 400g",
          image: "images/cereal/kukuruzne_zitarice.png",
        },
        {
          id: 29,
          name: "Voćne žitarice, 300g",
          image: "images/cereal/vocne_zitarice.png",
        },
        {
          id: 30,
          name: "Zobene žitarice, 500g",
          image: "images/cereal/zobene_zitarice.png",
        },
      ],
    },
    {
      name: "Slatkiši",
      products: [
        {
          id: 31,
          name: "Čokolada, 100g",
          image: "images/sweets/cokolada.png",
        },
        {
          id: 32,
          name: "Čokoladne kekse, 250g",
          image: "images/sweets/cokoladne_kekse.png",
        },
        {
          id: 33,
          name: "Bomboni s okusom jagode, 300g",
          image: "images/sweets/jagoda_bomboni.png",
        },
        {
          id: 34,
          name: "Kiseli gumeni bomboni, 250g",
          image: "images/sweets/kiseli_bomboni.png",
        },
        {
          id: 35,
          name: "Lizalice, 150g",
          image: "images/sweets/lizalica.png",
        },
      ],
    },
    {
      name: "Mliječni proizvodi i jaja",
      products: [
        {
          id: 36,
          name: "Jaja, 10 komada",
          image: "images/dairy_eggs/jaja.png",
        },
        {
          id: 37,
          name: "Tekući jogurt, 500ml",
          image: "images/dairy_eggs/jogurt.png",
        },
        {
          id: 38,
          name: "Maslac, 300g",
          image: "images/dairy_eggs/maslac.png",
        },
        {
          id: 39,
          name: "Kravlje mlijeko, 1L",
          image: "images/dairy_eggs/mlijeko.png",
        },
        {
          id: 40,
          name: "Gouda sir, 500g",
          image: "images/dairy_eggs/sir.png",
        },
      ],
    },
    {
      name: "Orašasti plodovi",
      products: [
        {
          id: 41,
          name: "Bademi, 200g",
          image: "images/nuts/badem.png",
        },
        {
          id: 42,
          name: "Indijski oraščići, 150g",
          image: "images/nuts/indijski_orah.png",
        },
        {
          id: 43,
          name: "Kikiriki, 400g",
          image: "images/nuts/kikiriki.png",
        },
        {
          id: 44,
          name: "Lješnjaci, 200g",
          image: "images/nuts/ljesnjak.png",
        },
        {
          id: 45,
          name: "Pistacije, 150g",
          image: "images/nuts/pistacije.png",
        },
      ],
    },
    {
      name: "Pića",
      products: [
        {
          id: 46,
          name: "Energetski napitak, 250ml",
          image: "images/drinks/energetski_napitak.png",
        },
        {
          id: 47,
          name: "Sok od jabuke, 1L",
          image: "images/drinks/sok_od_jabuke.png",
        },
        {
          id: 48,
          name: "Sok od maline, 1L",
          image: "images/drinks/sok_od_maline.png",
        },
        {
          id: 49,
          name: "Sok od naranče, 1L",
          image: "images/drinks/sok_od_narance.png",
        },
        {
          id: 50,
          name: "Mineralna voda, 1.5L",
          image: "images/drinks/voda.png",
        },
      ],
    },
  ],
};

module.exports = data;
