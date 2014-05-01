use strict;
use warnings;
use 5.010;

say "Please enter 2 numbers:";
my $number1 = <STDIN>;
my $number2 = <STDIN>;

chomp $number1;
chomp $number2;

my $sum = $number1 + $number2;

say "number1: $number1, number2: $number2 and sum: $sum";

