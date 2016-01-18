
part of tatort_campus_app;

/**
 * Ein Dialog zum Anzeigen der fallengelassenen Bilder auf dem Uploadbereich und zum Ausw&auml;hlen des Kategorie Vorschaubildes
 */
class GallerieUploaderDialog{

  DivElement _vorschauDialog;
  DivElement vorschauBildContainer = new DivElement();
  DivElement _ausgewaehlteVorschau;
  DivElement _kategorieDatenDiv;

  SpanElement sendenLabel = new SpanElement();
  SpanElement cancelLabel = new SpanElement();

  InputElement _kategorieTitelTextbox = new InputElement();

  Map<String, ImageElement> _detailBilder = new Map<String, ImageElement>();
  Map<String, ImageElement> _vorschauBilder = new Map<String, ImageElement>();
  ImageElement _kategorieBild = new ImageElement();
  num _schnittSeitenVerhaeltnis;
  num _bildGroesse;
  num _qualitaet;
  num _kategorieBildGroesse;
  num _vorschauBildGroesse;
  
  GallerieUploaderDialog(this._qualitaet, this._bildGroesse, this._kategorieBildGroesse , this._vorschauBildGroesse, this._schnittSeitenVerhaeltnis){

      _vorschauDialog = new DivElement();
      _vorschauDialog.classes.add("gallerie_uploader");

      var kategorieBildDiv = new DivElement();
      kategorieBildDiv..classes.add("kategorie_bild_div")
                      ..append(_kategorieBild)
                      ..style.width = "${_kategorieBildGroesse}px"
                      ..style.height = "${_kategorieBildGroesse / _schnittSeitenVerhaeltnis}px";

      _kategorieDatenDiv = new DivElement();
      _kategorieDatenDiv..classes.add("kategorie_daten_div")
                        ..style.height =
                        "${_kategorieBildGroesse / _schnittSeitenVerhaeltnis}px";

      var titel = new Element.html("<h1>Kategorie-Titel</h1>");

      _kategorieTitelTextbox..classes.add("kategorie_titel_textbox")
                            ..id = "kategorie_titel"
                            ..type = "text";

      sendenLabel..classes.add("gallerie_senden_label")
                 ..text = "Hochladen"
                 ..onClick.listen(_sendenClick);

      cancelLabel..classes.add("gallerie_abbrechen_label")
                 ..text = "Abbrechen"
                 ..onClick.listen(_abbrechenClick);

      _kategorieDatenDiv..append(titel)
                        ..append(_kategorieTitelTextbox);

      _vorschauDialog..append(kategorieBildDiv)
                     ..append(_kategorieDatenDiv)
                     ..append(vorschauBildContainer)
                     ..style.visibility = "collapse";

      query('body').append(_vorschauDialog);
  }

  /**
   * Sendet die fallengelassenen zugeschnittenen 
   * und skalierten Bilder als Blobs mithilfe einer 
   * asynchronen Anfrage an den Server. Bei Antwort 
   * vom Server, wird der Bereich f&uuml;r die Galerien neu geladen.
   */
  void _sendenClick(MouseEvent evt){
    MultipartRequest request = new MultipartRequest("/TatortCampusCMS/webresources/gallery/upload");    

    for(var i = 0; i < _detailBilder.length; i++){      
      request.appendBlob("detail{i}", uriToBlob(_detailBilder['${i}'].src));
      request.appendBlob("thumbnail{i}", uriToBlob(_vorschauBilder['${i}'].src));      
    }    
    request.append("categoryTitle", _kategorieTitelTextbox.value);
    request.appendBlob("categoryImage", uriToBlob(_kategorieBild.src));
    
    request.send().then((xml){ 
      updateContainer(xml);
      _vorschauDialog.remove();  
    });    
  }
 
  /**
   * Beendet den Vorgang und entfernt den Dialog.
   */
  void _abbrechenClick(MouseEvent evt){
    _vorschauDialog.remove();
  }

  /**
   * L&auml;dt das fallengelassene Bild ein und zeigt es an.
   */
  Future ladeBild(File datei){
    if(_vorschauDialog.style.visibility == "collapse")
      _vorschauDialog.style.visibility = "visible";

    var ret = new Completer();

    new FileReader()..onLoadEnd.listen((fileEvt){

      var img = new ImageElement(src: fileEvt.target.result);
      img.onLoad.listen((imgEv){

        var min = math.min(img.width, img.height);
        var max = math.max(img.width, img.height);
        var div = min / _bildGroesse;

        var neuWidth = (img.width / div).floor().toInt();
        var neuHeight = (img.height / div).floor().toInt();

        var detailBild = skaliereBild(img, _qualitaet, 0,0, img.width, img.height, 0,0, neuWidth, neuHeight);;
        detailBild.then((ImageElement resultImage){
          var index = _detailBilder.length.toString();
          _detailBilder[index] = new ImageElement(src: resultImage.src);
          ret.complete();
        });

        div = max / _vorschauBildGroesse;

        neuWidth = (img.width / div).floor().toInt();
        neuHeight = (img.height / div).floor().toInt();

        var vorschauBild = skaliereBild(img, _qualitaet, 0,0, img.width, img.height, 0,0, neuWidth, neuHeight);;
        vorschauBild.then((ImageElement resultImage){
          var index = _vorschauBilder.length.toString();
          _vorschauBilder[index] = new ImageElement(src: resultImage.src);
          fuegeVorschauBildHinzu(_vorschauBilder[index], index);
        });

    });
  })
  ..readAsDataUrl(datei);

    return ret.future;
  }

  /**
   * Setzt das Vorschaubild auf dem Dialog ein.
   */
  void fuegeVorschauBildHinzu(ImageElement img, String index){
    var vorschauBildDiv = new DivElement();
    vorschauBildDiv.style..width = "${_vorschauBildGroesse + 20}px"
                         ..height = "${_vorschauBildGroesse + 20}px"
                         ..background = "url('${img.src}') no-repeat center center";

   vorschauBildDiv..id = index
                  ..classes.add("gallerie_vorschau_bild")
                  ..onClick.listen(_vorschauBildClick);

    vorschauBildContainer.append(vorschauBildDiv);
  }

  /**
   * Reagiert auf das Klicken des Vorschaubildes mit 
   * dem Markieren dieses Bildes als Kategorie-Vorschau-Bild 
   * und zeigt bei Bedarf den Dialog zum Zuschneiden des 
   * Vorschaubildes an, damit es den voreingestellten Proportionen entspricht.
   */
  void _vorschauBildClick(MouseEvent evt){
    if(_ausgewaehlteVorschau != null) {
      _ausgewaehlteVorschau.style.boxShadow = "inset 0px 0px 0px 1px #c6c6c6";
    }

    _ausgewaehlteVorschau = (evt.currentTarget as DivElement);
    _ausgewaehlteVorschau.style.boxShadow = "inset -1px -1px 0px 0px #c6c6c6,inset 0px 0px 50px 7px #66cc11";

    var kategorieBildBreite = _kategorieBildGroesse;
    var kategorieBildHoehe =
        (_kategorieBildGroesse / _schnittSeitenVerhaeltnis)
        .round().toInt();

    var bild = CropDialog.zeigDialog
        (_detailBilder[_ausgewaehlteVorschau.id],
            _schnittSeitenVerhaeltnis,
            kategorieBildBreite, kategorieBildHoehe);

    bild.then((result) {
      if(result != null){
        _kategorieBild.src = result.src;
        _kategorieDatenDiv..append(sendenLabel)
                       ..append(cancelLabel);
      }
    });
  }
}
