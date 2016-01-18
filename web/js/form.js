function edit(o) {
    if(o.value == "Name" || o.value == "E-Mail" || o.value == "Gebt hier euren Text ein."
	|| o.value == "Ich möchte bei der Tatort Campus mitmachen!") 
        o.value = ""; 
}

function leer(o, i) {
    if(o.value == "") {
        switch(i) {
            case 0: o.value = "Name";
                break;
            case 1: o.value = "E-Mail"
                break;
            case 2: o.value = "Gebt hier euren Text ein.";
                break;
            case 3: o.value = "Ich möchte bei der Tatort Campus mitmachen!";
        }
    }
}