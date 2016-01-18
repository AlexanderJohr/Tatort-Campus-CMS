part of tatort_campus_app;

/**
 * Ein Dialog, zum Ausw&auml;hlen des Bildbereiches der
 * vor eingestellten Proportionen mithilfe von Drag-and-drop
 * 
 * @author Alexander Johr u26865 m18927
 */
class CropDialog{

  Rechteck zielRechteck;
  DivElement div = new DivElement();
  DivElement imgDiv = new DivElement();
  ImageElement imgData;
  SpanElement cropLabel = new SpanElement();
  SpanElement cancelLabel = new SpanElement();
  CropChooser chooser;
  Completer _ret = new Completer();
  num _schnittSeitenVerhaeltnis;
  num _qualitaet;

  CropDialog(this.imgData, this._schnittSeitenVerhaeltnis, num neueBreite, num neueHoehe){
    zielRechteck = new Rechteck(0, 0, neueBreite, neueHoehe);

    div.classes.add("crop_dialog");

    imgDiv..id="crop_bild"
          ..style.position = "relative";

    cropLabel..text = "Ausschneiden"
             ..onClick.listen(_ausschneidenClick)
             ..classes.add("crop_crop_label");

    cancelLabel..text = "Abbrechen"
               ..onClick.listen(_abbrechenClick)
               ..classes.add("crop_cancel_label");

    imgData..alt="zuzuschneidendesBild";

    _bildGeladen();

    query('body').append(div);
  }

  /**
   * Zeige den Dialog und liefere das Ergebnis, 
   * wenn es verf&uuml;gbar ist
   */
  static Future<ImageElement> zeigDialog(ImageElement img, num schnittSeitenVerhaeltnis, num neueBreite, num neueHoehe){
    var dialog = new CropDialog(img, schnittSeitenVerhaeltnis, neueBreite, neueHoehe);
    return dialog._ret.future;
  }


  /**
   * Aufgerufen, wenn die Datei geladen wurde. 
   * Berechnet die Abweichung vom voreingestellten 
   * Seitenverh&auml;ltnis und erstellt den Auswahlbereich
   * zum Zuschneiden des Bildes.
   */
  void _bildGeladen(){
    var bildSeitenVerhaeltnis = imgData.width / imgData.height;
    if(bildSeitenVerhaeltnis != _schnittSeitenVerhaeltnis){

      // Bild geladen => Buttons sichtbar machen
      div.append(imgDiv);
      div.append(cropLabel);
      div.append(cancelLabel);

      if(bildSeitenVerhaeltnis > _schnittSeitenVerhaeltnis){
        chooser = new CropChooser(imgData.height  * _schnittSeitenVerhaeltnis, imgData.height);
      } else{
        chooser = new CropChooser(imgData.width, imgData.width  / _schnittSeitenVerhaeltnis);
      }
      chooser.div.draggable = true;
      imgDiv.style..backgroundImage = "url('${imgData.src}')"
                  ..width = "${imgData.width}px"
                  ..height = "${imgData.height}px";
      imgDiv.append(chooser.div);
    } else {
      var quellRechteck = new Rechteck(0,0,imgData.width, imgData.height);

      var result = skaliereBildNachRechteck(imgData, _qualitaet, quellRechteck, zielRechteck);
      result.then((ImageElement img){
        _ret.complete(img);
      });

      div.remove();
    }
  }

  /**
   * Best&auml;tigt die Auswahl des Auswahlbereiches
   * und schneidet das Bild dementsprechend zu
   */
  void _ausschneidenClick(MouseEvent evt){
    var result = skaliereBildNachRechteck(imgData, _qualitaet, chooser.ausschnitt, zielRechteck);
    result.then((ImageElement img){
    _ret.complete(img);
    });
    div.remove();
  }

  /**
   * Bricht die Aktion ab.
   */
  void _abbrechenClick(MouseEvent evt){
    _ret.complete(null);
    div.remove();
  }

}
