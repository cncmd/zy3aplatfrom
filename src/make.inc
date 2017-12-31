define __main__
	cat $1 >> $2
endef

define __define__
    @cat $1$2>>$3
endef

define __case__
    @cd $1$2 && $3 $4 $2 
endef

define __version__
    @echo "//\version[$2]\;" > $1 
endef

define __definedir__
    @cat $1/*.groovy >> $2
endef
	
define __replaceWindowsImport__
	@sed -i "s/package com./ \/\/ /g" $1
	@sed -i "s/import com.agent/ \/\/ /g"  $1
endef

define __replaceOtherImport__
	@sed -i ".bak" "s/package com./ \/\/ /g" $1
	@sed -i ".bak" "s/import com./ \/\/ /g"  $1
endef
