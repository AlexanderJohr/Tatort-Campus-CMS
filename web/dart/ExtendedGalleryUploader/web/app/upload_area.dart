part of tatort_campus_app;
/**
 * Ein Bereich zum Fallenlassen 
 * von Dateien zum Hochladen dieser Dateien auf den Server.
 * 
 * @author Alexander Johr u26865 m18927
 */
abstract class UploadBereich{
  
  
  Element uploadBereich;
  
  UploadBereich(this.uploadBereich){    
    uploadBereich..onDragOver.listen(onDragOver)
    ..onDragLeave.listen(onDragLeave)
    ..onDrop.listen(onDrop);
  }  
  
  /**
   * Kommt der Mauszeiger mit selektierten Dateien &uuml;ber
   * den Bereich, wird dieser dementsprechend markiert.
   */
  onDragOver(MouseEvent evt){
    evt.preventDefault();
    uploadBereich.style.border = "5px dashed blue";
  }
  /**
   * Markierung wird wieder entfernt, wenn der Mauszeiger sich nicht mehr im Bereich befindet.
   */
  onDragLeave(MouseEvent evt){
    evt.preventDefault();
    uploadBereich.style.borderStyle = "none";
  } 
  
  /**
   * Werden auf dem Bereich Dateien fallengelassen, 
   * wird auch die Markierung entfernt. Diese Methode 
   * wird von den konkreten Uploadbereichen &uuml;berschrieben.
   */
  onDrop(MouseEvent evt){
    evt.preventDefault();
    uploadBereich.style.borderStyle = "none";
  }  
}
  
