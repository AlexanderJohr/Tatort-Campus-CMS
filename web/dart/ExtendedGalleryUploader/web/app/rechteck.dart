
part of tatort_campus_app;

/**
 * Eine Hilfsklasse zum Abbilden eines Rechtecks.
 * 
 * @author Alexander Johr u26865 m18927
 */
class Rechteck {
  int left, top, width, height;

  int get right             => left + width;
      set right(int value)  => left = value - width;

  int get bottom            => top + height;
      set bottom(int value) => top = value - height;

  Rechteck(this.left, this.top, this.width, this.height);
}