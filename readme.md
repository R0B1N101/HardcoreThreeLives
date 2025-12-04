# HardcoreThreeLives

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
![Java](https://img.shields.io/badge/Java-21-orange)
![Minecraft](https://img.shields.io/badge/Paper-1.21.x-blue)
![Status](https://img.shields.io/badge/State-Production%20Ready-brightgreen)

Een Minecraft (Paper) plugin die hardcore transformeert naar een **levenssysteem**:

- 🩸 Elke speler krijgt een beperkt aantal levens (standaard **3**)
- 🔁 Zolang je levens hebt, kun je blijven respawnen
- 💀 Zodra je levens op zijn krijgt **elke volgende death een tijdelijke ban**
- 💾 Deaths worden opgeslagen in `data.yml` → blijven bestaan na restarts
- 🇳🇱 Nederlandse tijd- en berichtweergave

---

## ✨ Features

| Functie | Beschrijving |
|--------|-------------|
| Levensysteem | Spelers hebben een beperkt aantal levens |
| Tempban bij death | Na levens op → elke death = ban |
| Configuratie | Volledig aanpasbaar via `config.yml` |
| Nederlandse tijd | Verbanning in NL-datumformaat |
| Commands | `/lives`, `/resetlives`, `/hardcore reload` |
| Persistente data | Deaths worden blijvend opgeslagen |

---

## 🛠 Installatie

1. Download de laatste release:  
   👉 **https://github.com/R0B1N101/HardcoreThreeLives/releases**
2. Plaats de `.jar` in je `plugins` map
3. Start de server
4. De plugin genereert automatisch:
   - `config.yml`
   - `data.yml`

---

## 💬 Commands

| Command | Beschrijving | Permissie |
|---------|-------------|----------|
| `/lives` | Laat deaths en resterende levens zien | Iedereen |
| `/hardcore reload` | Herlaadt `config.yml` | OP |
| `/resetlives <speler>` | Zet deaths van speler op 0 | OP |

---

## ⚙️ Configuratie

Standaard `config.yml`:

```yaml
lives:
  # Hoeveel levens een speler in totaal krijgt
  maxLives: 3

ban:
  # Minuten ban bij de eerste keer dood zonder levens
  baseMinutes: 60

  # Extra minuten ban per volgende dood nadat levens op zijn
  # 0 = altijd 60 min, 5 = 60, 65, 70, 75...
  extraMinutesPerDeath: 0

formatting:
  # Hoe datum/tijd wordt weergegeven in Nederlandse stijl
  # Voorbeeld: "donderdag 4 december 2025 om 04:09 uur"
  dateTime: "EEEE d MMMM yyyy 'om' HH:mm 'uur'"

messages:
  # Je gaat dood maar hebt nog levens
  deathStillLives: "&cJe bent doodgegaan! Je hebt nog &e{remaining} &ckansen over."

  # Geen levens meer → waarschuwing dat de volgende dood ban is
  deathNoLivesLeft: "&cJe hebt al je &e{maxLives} &ckansen verbruikt! Volgende death is een &4ban&c."

  # Kick message bij ban
  deathBannedKick: "&cJe bent doodgegaan zonder levens!\n&fVerbannen tot:\n&e{time}"

  # Reden zichtbaar in banlist
  banReason: "Geen levens meer in hardcore!"
