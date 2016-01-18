part of tatort_campus_app;

/**
 * Ein Uploadbereich f&uuml;r die Logos
 * 
 * @author Alexander Johr u26865 m18927
 */
class LogoUploadBereich extends UploadBereich{

  List<String> links = new List<String>();
  List<String> logos = new List<String>();

  int uebrigeLogos;

  Set<String> _imageTypes = new Set<String>.from(['image/jpeg', 'image/png']);
  num _logoBreite = 125;
  num _seitenVerhaeltnis = 1.3157894736842105263157894736842;

  LogoUploadBereich(Element uploadBereich) : super(uploadBereich){

  }

  void onDragOver(MouseEvent evt){
    super.onDragOver(evt);
  }

  void onDragLeave(MouseEvent evt){
    super.onDragLeave(evt);
  }

  void onDrop(MouseEvent evt){
    super.onDrop(evt);

    var fallenGelasseneBilder = evt.dataTransfer.files;
    if(!fallenGelasseneBilder.isEmpty){
      uebrigeLogos = fallenGelasseneBilder.length;
      _ladeLogos(fallenGelasseneBilder, 0);

    }
  }

  /**
   * Lade die fallengelassenen Bilder synchron. 
   * In Google Dart gibt es bisher keine M&ouml;glichkeit 
   * auf einen asynchronen Methoden-Aufruf zu warten. 
   * Das Laden der Datei ist ein solcher asynchroner Aufruf. 
   * Daher wird beim Callback die Methode nochmal aufgerufen, 
   * bis alle Logos geladen worden.
   */
  Future _ladeLogos(List<File> dateien, int i){
    if(i < dateien.length) {
      if(_imageTypes.contains(dateien[i].type)){
        print("vor$i");
        _ladeLogo(dateien[i]).then((obj){
          print("then$i");

          i++;
          _ladeLogos(dateien, i);
        });
      } else {
        print("Format nicht untersützt.");
      }

    }
  }

  /**
   * Aufgerufen wenn das Logo geladen wurde, skaliert das Bild.
   */
  Future _ladeLogo(File datei){

    var ret = new Completer();

    new FileReader()..onLoadEnd.listen((fileEvt){

      var img = new ImageElement(src: fileEvt.target.result);
      img.onLoad.listen((imgEv){

        num logoHoehe = _logoBreite / _seitenVerhaeltnis;


        var max = math.max(img.width, img.height);
        var originalSeitenVerhaeltnis = img.width / img.height;
        var div = max / _logoBreite;

        if(originalSeitenVerhaeltnis < _seitenVerhaeltnis){
          div = max / logoHoehe;
        }

        var neuWidth = (img.width / div).floor().toInt();
        var neuHeight = (img.height / div).floor().toInt();

        var bild = skaliereBild(img, 0.95, 0,0, img.width, img.height, 0,0, neuWidth, neuHeight);
        bild.then((result) {

          if(result != null){

            var link = LogoLinkDialog.zeigeDialog(result);

            link.then((String linkText){
              if(link != null){
                links.add(linkText);
                logos.add(result.src);
              }
              ret.complete(true);
              uebrigeLogos--;

              if(uebrigeLogos == 0){
                _sendeDaten();
              }
            });
          }
        });
      });

  })
  ..readAsDataUrl(datei);
    print("lesen angefangen");
    
    return ret.future;
  }


  /**
   * Sendet Logos zusammen mit dem entsprechenden Links an den Server.
   */
  void _sendeDaten(){
    // Anfrage an den Rest-Service erstellen
    MultipartRequest request = 
        new MultipartRequest("/TatortCampusCMS/webresources/logo/upload");
    
    // Felder der Anfrage setzen
    for(var i = 0; i < links.length; i++){
      request.appendBlob("logo${i}", uriToBlob(logos[i]));
      request.append("link${i}", links[i]);      
    }
    // Listen der Links und logos leeren
    links.clear();
    logos.clear(); 
    // Senden und auf Antwort mit Update des Containers reagieren
    request.send().then((xml){ updateContainer(xml); });
  }
}



