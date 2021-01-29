1. Changes to Red vs. Black
- In this branch, I included some of the changes I made to the red vs. black feature when we were working on it in late November. I would recommemd implementing it when you have a chance, as it might make it easier for users to know when to complete surveys

2. Numeric
- This is for users to enter a number to a question (e.g how many times have you done this? How many days since? etc.)

3. Rating
- This is for features to rate something but in a bounded way. It was originally supposed to be a slider (like the Rating feature in iOS) but there was no native slider in the Formmaster framework. So I made something that could work in tangent with the equivalent rating feature in iOS.

4. Likert
- The Likert scale is a scale that allows the user to rate their experience along a scale (See https://en.wikipedia.org/wiki/Likert_scale). Dr. Poellabauer had me make a 4-pt, 5-pt, and 7-pt scale. The user would theoretically add options for the scale but also can select to use the preset (i.e standard) options. In addition, the user can select to use a legend that shows numbers instead of the actual options themselves in order to save space (there is a key below the question).
