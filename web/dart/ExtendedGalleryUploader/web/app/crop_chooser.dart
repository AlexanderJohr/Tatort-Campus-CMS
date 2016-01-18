part of tatort_campus_app;

/**
 * Legt den Ausschneidebereich f&uuml;r das Kategorie-Bild fest.
 * 
 * Es ist ein gr&uuml;nes Div-Element, 
 * welches den Proportionen des Kategorie-Bildes entspricht. 
 * Es kann &uuml;ber das Bild verschoben werden, 
 * ohne &uuml;ber die R&auml;nder des Vaters hinauszuragen.
 * 
 * @author Alexander Johr u26865 m18927
 */
class CropChooser{
  DivElement div = new DivElement();

  num _dargStartX;
  num _dargStartY;

  set left(num left) {
    if(left < div.parent.clientLeft){
      div.style.left = "${div.parent.clientLeft}px";
    } else if(left + div.clientWidth > div.parent.clientWidth) {
      div.style.left = "${div.parent.clientWidth - div.clientWidth}px";
    } else {
      div.style.left = "${left}px";
    }
  }
  set top(num top) {
    if(top < div.parent.clientTop){
      div.style.top = "${div.parent.clientTop}px";
    } else if(top + div.clientHeight > div.parent.clientHeight) {
      div.style.top = "${div.parent.clientHeight - div.clientHeight}px";
    } else {
      div.style.top = "${top}px";
    }
  }

  CropChooser(num width, num height){
    div = new DivElement();
    div..id="crop_chooser"
       ..onDrag.listen(_onDrag)
       ..onDragStart.listen(_onDragStart)
       ..onDragEnd.listen(_onDragEnd)
       ..classes.add("crop_chooser");

    div.style..width = "${width}px"
             ..height = "${height}px";
  }

  Rechteck get ausschnitt => new Rechteck(div.offsetLeft, div.offsetTop,
                                          div.offsetWidth, div.offsetHeight);

  void _onDragStart(MouseEvent evt){
    _dargStartX = div.offsetLeft  - evt.clientX;
    _dargStartY = div.offsetTop  - evt.clientY;
  }

  void _onDrag(MouseEvent evt){
    evt.preventDefault();
    left = evt.clientX  + _dargStartX;
    top = evt.clientY  + _dargStartY;
  }

  void _onDragEnd(MouseEvent evt){
    left = evt.clientX  + _dargStartX;
    top = evt.clientY  + _dargStartY;
  }


}
