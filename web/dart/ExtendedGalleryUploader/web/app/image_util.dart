part of tatort_campus_app;

/**
 * Zeichnet das Bild auf einem HTML5 Canvas um es den Anforderungen an die Breite und H&ouml;he anzupassen.
 */
Future<ImageElement> skaliereBild(ImageElement img, num qualitaet, num sX, num sY, num sW, num sH, num dX, num dY, num dW, num dH){
  var ret = new Completer();

  var canvas = new CanvasElement();
  canvas..width = dW
        ..height = dH;
  var context = canvas.context2d;
  
Rect source = new Rect(sX, sY, sW, sH);
Rect dest = new Rect(dX, dY, dW, dH);

context.drawImageToRect(img, dest, sourceRect: source);

  
  var skaliertesBild = new ImageElement();
  skaliertesBild..src = canvas.toDataUrl("image/jpeg", qualitaet)
                ..onLoad.listen(
                    (evt) => ret..complete(skaliertesBild),
                    onError: (err) => print(err.stackTrace));

  return ret.future;
}

/**
 * Zeichnet das Bild auf einem HTML5 Canvas um es den Anforderungen an die Breite und H&ouml;he anzupassen.
 */
Future<ImageElement> skaliereBildNachRechteck(ImageElement img, num qualitaet, Rechteck sR, Rechteck dR){
  return skaliereBild(img, qualitaet,
                      sR.left, sR.top, sR.width, sR.height,
                      dR.left, dR.top, dR.width, dR.height);
}

/**
 * Wandelt eine Base64-URL in einen Blob um
 */
Blob uriToBlob(String url){
  // entferne Base64-Dateityp (data:image/jpg;base64,...)
  var byteString = window.atob(url.split(',')[1]);
  // Wandle Base64-Byte-String in Bytes um.
  var arrayBuffer = new Uint8List(byteString.length);
  var dataArray = new Uint8List.view(arrayBuffer.buffer);
  for (var i = 0; i < byteString.length; i++) {
    dataArray[i] = byteString.codeUnitAt(i);
  }
  // Erstelle Blob aus dem Byte-Array.
  var blob = new Blob([arrayBuffer], 'image/jpg');
  return blob;
}

