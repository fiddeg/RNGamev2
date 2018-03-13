<?xml version="1.0" encoding="UTF-8"?>
<tileset name="level1" tilewidth="128" tileheight="128" tilecount="4" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <terraintypes>
  <terrain name="Ground" tile="0"/>
  <terrain name="Lava" tile="1"/>
  <terrain name="Water" tile="2"/>
  <terrain name="Floatin Ground" tile="3"/>
 </terraintypes>
 <tile id="0" type="Ground" terrain="1,1,1,1">
  <image width="128" height="128" source="leafy_ground01.png"/>
 </tile>
 <tile id="1" type="Lava" terrain="1,1,1,1">
  <image width="128" height="128" source="lava_1.png"/>
  <objectgroup draworder="index">
   <object id="1" x="-2" y="42" width="132" height="84"/>
  </objectgroup>
 </tile>
 <tile id="2" type="Water" terrain="1,1,1,1">
  <image width="128" height="128" source="water1.png"/>
  <objectgroup draworder="index">
   <object id="1" x="0" y="41" width="128" height="86"/>
  </objectgroup>
 </tile>
 <tile id="3" type="Float_Ground" terrain="2,2,2,2">
  <image width="128" height="128" source="leafy_ground03.png"/>
  <objectgroup draworder="index">
   <object id="1" x="0" y="0" width="128" height="82"/>
  </objectgroup>
 </tile>
</tileset>
