# HardcoreThreeLives

Een Minecraft (Paper) plugin die hardcore verandert in een levenssysteem:

- Elke speler krijgt een beperkt aantal levens (standaard 3).
- Zolang je nog levens hebt kun je blijven spelen.
- Zodra je levens op zijn, zorgt **elke volgende death voor een tijdelijke ban**.
- Deaths worden opgeslagen in `data.yml`, dus blijven bestaan na server restarts.

## Features

- ✅ Configureerbaar aantal levens (`lives.maxLives`)
- ✅ Tijdelijke ban na het opmaken van je levens
- ✅ Bantijd kan schalen per extra ban-death
- ✅ Nederlandse datum/tijd weergave in de kick message
- ✅ Commands:
  - `/lives` – laat je deaths en resterende levens zien
  - `/resetlives <speler>` – reset deaths (alleen OP)
  - `/hardcore reload` – herlaadt de config (alleen OP)

## Vereisten

- Java 21
- Paper of Spigot 1.21.x
- (Optioneel) Maven om de plugin zelf te bouwen

## Installatie

1. Download de laatste `.jar` uit de **Releases** sectie (of bouw zelf met Maven).
2. Plaats de jar in de `plugins` map van je server.
3. Start of restart de server.
4. De plugin maakt automatisch:
   - `config.yml`
   - `data.yml`

## Configuratie

Standaard `config.yml`:

lives:
  # Hoeveel levens een speler in totaal krijgt
  maxLives: 3

ban:
  # Minuten ban bij de eerste keer dood zonder levens
  baseMinutes: 60

  # Extra minuten ban per volgende dood nadat levens op zijn
  # Voorbeeld: 0 = altijd 60 min, 5 = 60, 65, 70, 75...
  extraMinutesPerDeath: 0

formatting:
  # Hoe datum/tijd wordt weergegeven in Nederland:
  # Voorbeeld: "donderdag 4 december 2025 om 04:09 uur"
  dateTime: "EEEE d MMMM yyyy 'om' HH:mm 'uur'"

messages:
  # Speler gaat dood maar heeft nog levens
  deathStillLives: "&cJe bent doodgegaan! Je hebt nog &e{remaining} &ckansen over."

  # Speler heeft geen levens meer
  deathNoLivesLeft: "&cJe hebt al je &e{maxLives} &ckansen verbruikt! Volgende death is een &4ban&c."

  # Kick message bij ban
  deathBannedKick: "&cJe bent doodgegaan zonder levens!\n&fVerbannen tot:\n&e{time}"

  # Reden die in /ban zichtbaar is
  banReason: "Geen levens meer in hardcore!"

