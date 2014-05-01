use strict;
use warnings;

use Scalar::Util qw(looks_like_number);

print "How many loaces of bread shall I buy? ";
my $loaves = <STDIN>;
chomp $loaves;

if (looks_like_number($loaves))
{
    print "I am on it...";
}
else
{
    print "Sorry did not get that.";
}