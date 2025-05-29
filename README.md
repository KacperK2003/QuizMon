# 🎮 QuizMon

> 🇵🇱 Wersja polska | 🇬🇧 [See the English version](README.eng.md)

## 👥 Autor
- Kacper Kozłowski ([@KacperK2003](https://github.com/KacperK2003))

## Opis projektu

**QuizMon** to aplikacja stworzona jako zaliczenie przedmiotu *Event-Driven Programming* na Wojskowej Akademii Technicznej. Projekt umożliwia:

- rozwiązywanie quizów w stylu „Co to za Pokémon?”,
- przeglądanie pokedexu,
- dodawanie Pokémonów do listy ulubionych.

Projekt korzysta z publicznego API: [PokeAPI](https://pokeapi.co/)  
Ma on charakter wyłącznie edukacyjny i nie jest przeznaczony do komercyjnego wykorzystania.

## 🧩 Funkcjonalności

- 🔍 Quiz z rozpoznawaniem Pokémonów
- 📖 Pokedex z wyszukiwarką
- ⭐ System ulubionych Pokémonów
- 🗃️ Lokalna baza danych SQLite do przechowywania danych

## 🛠️ Technologie

- **Język programowania:** Java
- **Framework:** JavaFX
- **Baza danych:** SQLite
- **Zarządzanie zależnościami:** Maven
- **JDK:** Oracle JDK 24
- **IDE:** JetBrains IntelliJ IDEA 2025

## 🚀 Uruchomienie projektu

1. Upewnij się, że masz zainstalowane:
   - Java JDK 24
   - Maven
   - IntelliJ IDEA

2. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/KacperK2003/QuizMon.git
   cd QuizMon
   ```
3. Uruchom projekt wykorzystując dołączoną konfigurację do InteliJ z komendą:
   ```bash
   mvn compile exec:java
   ```

## Licencje

Kod źródłowy: objęty licencją MIT License

Czcionki: używana czcionka objęta jest licencją SIL Open Font License

Zależności zewnętrzne:

    JavaFX (GPL + Classpath Exception)

    SQLite JDBC Driver (Apache 2.0)

    Inne biblioteki open-source opisane w pom.xml, każda objęta własną licencją

Informacje prawne

    Pokémon to zastrzeżony znak towarowy należący do Nintendo, Game Freak i The Pokémon Company.

    Ten projekt ma charakter wyłącznie edukacyjny i nie jest powiązany, sponsorowany ani zatwierdzony przez wyżej wymienione podmioty.
