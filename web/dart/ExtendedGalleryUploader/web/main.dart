import 'app/tatort_campus_app.dart';

import 'dart:html';

void main() {
  
  queryAll('.rest_link').forEach((Element link){
    new RestLink(link);
  });
  
  queryAll('#galleries_box').forEach((Element bereich){
    new GallerieUploadBereich(bereich
        ,0.95, 600, 259, 150, 1.0926640926640926640926640926641);
  });
  
  queryAll('#logos_box').forEach((Element bereich){
    new LogoUploadBereich(bereich);
  });

  queryAll('#magazines_box').forEach((Element bereich){  
    new PdfUploadBereich(bereich);
  });
  
  new SeitenInhaltUpdateDialog();
}

