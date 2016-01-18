part of tatort_campus_app;

/**
 * Ein Uploadbereich f&uuml;r die Magazine
 * 
 * @author Alexander Johr u26865 m18927
 */
class PdfUploadBereich extends UploadBereich{

  GallerieUploaderDialog dialog;

  PdfUploadBereich(Element uploadBereich) : super(uploadBereich){
  }

  /**
   * Wird eine PDF auf dem Bereich fallengelassen, 
   * wird diese sofort gesendet.
   */
  void onDrop(MouseEvent evt){
    super.onDrop(evt);
    var fallenGelassenePDFs = evt.dataTransfer.files;
    if(!fallenGelassenePDFs.isEmpty){
      _sendePDF(fallenGelassenePDFs[0]);
    }
  }

  /**
   * Sendet mithilfe eines asynchronen MultipartRequest die PDF Datei an den Server. 
   */
  void _sendePDF(datei){    
    MultipartRequest request = new MultipartRequest("/TatortCampusCMS/webresources/magazine/upload");    
    request.appendBlob("meinePdf", datei);   
    request.send().then((xml){ updateContainer(xml); });
  }
}



