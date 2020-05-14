# Kamehameha: a text-based multiplayer game

### Created by Boxian Wang, May 1 2020

## Introduction

Kamehameha is a text-based multiplayer game written in Java. It is inspired by a similar game
played by some of my friends in elementary game.

To start with, two players each has three stats: **health**, **breath**, and **defense**.

Each player has three moves at each turn: **attack**, **defend**, and **breathe**.

**Attack** takes 1 breath, and will strike the opponent if (s)he chooses to **breathe**. This
would result in opponent losing health.

**Defend** takes 1/2 breath and can block an **attack**.

**Breath** increases breath by 1.

Players take turn to name their move, and game is over when one player's health reaches 0.

## Implementation

This tiny project is written in Java. The server-client model is inspired by the course
material of CS10 at Dartmouth College. 

I adapted the original framework so that it would host a text-based multiplayer game.

The implementation utilized multi-threading and socket programming. 

The source code right now is configured so that server can run on localhost.
I will continue to make adaptation so that it might be publicly hosted.

## Other

I wrote this project using IDEA, so if you have it as well you can easily pull and run it.
I will update on its compilation without an IDE.