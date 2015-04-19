use strict;
use warnings;

use File::Copy;
foreach my $file (<itemsold/*.png>)
{
    my $newfile = lc $file;
    $newfile =~ s/itemsold\//items\/animalsdropbones_/;
    print $newfile."\n";
    copy($file,$newfile) or die "Copy failed: $!";   
}