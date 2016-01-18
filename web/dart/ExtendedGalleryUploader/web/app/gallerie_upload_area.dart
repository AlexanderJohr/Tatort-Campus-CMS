part of tatort_campus_app;

/**
 * Ein Uploadbereich f&uuml;r die Gallerie-Bilder
 * 
 * @author Alexander Johr u26865 m18927
 */
class GallerieUploadBereich extends UploadBereich{
  
  GallerieUploaderDialog dialog;
  
  Set<String> _imageTypes = new Set<String>.from(['image/jpeg', 'image/png']);
  
  
  // TODO REFACTOR SINGLETON
  num qualitaet, bildGroesse, kategorieBildGroesse, vorschauBildGroesse, schnittSeitenVerhaeltnis;
// TODO REFACTOR SINGLETON
  
  
  GallerieUploadBereich(Element uploadBereich, this.qualitaet, this.bildGroesse, 
                        this.kategorieBildGroesse , this.vorschauBildGroesse, 
                        this.schnittSeitenVerhaeltnis) : super(uploadBereich){
  }
  
  void onDragOver(MouseEvent evt){
    super.onDragOver(evt);
  }
  
  void onDragLeave(MouseEvent evt){ 
    super.onDragLeave(evt);
  } 
  
  void onDrop(MouseEvent evt){
    super.onDrop(evt);
    
    dialog = new GallerieUploaderDialog(qualitaet, bildGroesse, 
        kategorieBildGroesse , vorschauBildGroesse,
        schnittSeitenVerhaeltnis);
    
    dialog.vorschauBildContainer..onDragOver.listen(onDragOver)
      ..onDragLeave.listen(onDragLeave)
        ..onDrop.listen(onDrop);
    
    var fallenGelasseneBilder = evt.dataTransfer.files;
    if(!fallenGelasseneBilder.isEmpty){
      _ladeBilder(fallenGelasseneBilder, 0);
    }
  } 
  
  void _ladeBilder(List<File> dateien, int i){
    if(i < dateien.length) {
      if(_imageTypes.contains(dateien[i].type)){
          dialog.ladeBild(dateien[i]).then((obj){
        });
      } else {
        print("Format nicht untersützt.");
      }
      i++;
      _ladeBilder(dateien, i);
    }
  }
}
  


