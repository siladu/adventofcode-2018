cat day1-input.txt | paste -s -d "\0" - | sed 's/^\+\(.*\)/\1/' | bc
