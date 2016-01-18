part of tatort_campus_app;

/**
 * Dialog zum Eingeben des zum Logo geh&ouml;renden Links.
 * 
 * @author Alexander Johr u26865 m18927
 */
class LogoLinkDialog{

  InputElement linkTextBox = new InputElement(type: "text");
  DivElement dialogDiv = new DivElement();
  Completer _ret = new Completer();

  static Future<String> zeigeDialog(ImageElement logo){
    var dialog = new LogoLinkDialog(logo);
    return dialog._ret.future;
  }

  LogoLinkDialog(ImageElement kategorieBild){

    dialogDiv.classes.add("logo_dialog");
    linkTextBox.classes.add("logo_link_textbox");

    var sendenLabel = new SpanElement();
    sendenLabel..classes.add("logo_senden_label")
               ..text = "Senden"
               ..onClick.listen(_sendenClick);

    var cancelLabel = new SpanElement();
    cancelLabel..classes.add("logo_cancel_label")
               ..text = "Abbrechen"
               ..onClick.listen(_abbrechenClick);

    dialogDiv..append(kategorieBild)
             ..append(linkTextBox)
             ..append(sendenLabel)
             ..append(cancelLabel);

    query('body').append(dialogDiv);

  }

  /**
   * Sendet den eingegebenen Link an den Logo Upload 
   * Bereich zur&uuml;ck, damit das Logo zusammen mit dem 
   * Link hochgeladen werden kann.
   */
  void _sendenClick(MouseEvent evt){
    dialogDiv.remove();
    _ret.complete(linkTextBox.value);
  }

  /**
   * Beendet den Vorgang und liefert keinen Link zur&uuml;ck.
   */
  void _abbrechenClick(MouseEvent evt){
    dialogDiv.remove();
    _ret.complete(null);
  }
}
