# HC-puzzle - HappyCube puzzle solver  

Inspired by test task

Construct HappyCube from given 6 5x5 blocks
Any block can be rotated or flipped

### input format

Block may be presented with a 16-bit integer

16 bits:

    high         low
    FEDCBA9876543210

bit is set => corresponding square is filled:

    01234
    FXXX5
    EXXX6
    DXXX7
    CBA98

### output format

- print input blocks
- print solution scheme in format:
      
         S3
      S2 S1 S4
         S5
         S6
      
or "No solution"

### Building and testing

1. Clone this repository
2. Install Java 8 and maven
3. Run ‘mvn clean test’ to test

### Example

input data: 43956 23387 17476 23210 47780 19012

output:

    Blocks:
    
    ..1.1 22.22 ..3.. .4.4. ..5.. ..6..
    11111 .222. .333. .4444 55555 .666.
    .111. 22222 33333 4444. .555. 66666
    11111 .222. .333. .4444 55555 .666.
    .1.11 22.22 ..3.. 44.4. 55.5. .6.6.
    
    Solution:
    
          ..6..
          .666.
          66666
          .666.
          .6.6.
    
    22.22 ..1.1 .5.5.
    .222. 11111 .5555
    22222 .111. 5555.
    .222. 11111 .5555
    22.22 .1.11 .5.55
    
          ..3..
          .333.
          33333
          .333.
          ..3..
    
          .4.4.
          4444.
          .4444
          4444.
          .4.44
    
    