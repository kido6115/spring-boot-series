#!/bin/bash

# 因mave site對多模組支援問題手動將檔名改動
find "$target/staging/" -name "project-reports.html" -execdir mv {} index.html \;
