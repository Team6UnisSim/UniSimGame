<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.11.0" name="Tileset" tilewidth="16" tileheight="16" tilecount="30" columns="10">
 <transformations hflip="1" vflip="1" rotate="1" preferuntransformed="0"/>
 <image source="Terrain.png" width="160" height="48"/>
 <wangsets>
  <wangset name="Road" type="edge" tile="6">
   <wangcolor name="Road" color="#ff0000" tile="-1" probability="1"/>
   <wangtile tileid="5" wangid="1,0,0,0,0,0,0,0"/>
   <wangtile tileid="6" wangid="1,0,0,0,1,0,0,0"/>
   <wangtile tileid="7" wangid="1,0,0,0,0,0,1,0"/>
   <wangtile tileid="8" wangid="1,0,1,0,0,0,1,0"/>
   <wangtile tileid="9" wangid="1,0,1,0,1,0,1,0"/>
  </wangset>
  <wangset name="Lake" type="mixed" tile="12">
   <wangcolor name="Water" color="#ff0000" tile="-1" probability="1"/>
   <wangtile tileid="11" wangid="1,0,0,0,0,0,0,0"/>
   <wangtile tileid="12" wangid="1,0,0,0,1,0,0,0"/>
   <wangtile tileid="13" wangid="1,0,0,0,0,0,1,0"/>
   <wangtile tileid="14" wangid="1,0,0,0,0,0,1,1"/>
   <wangtile tileid="20" wangid="1,0,1,0,0,0,1,0"/>
   <wangtile tileid="21" wangid="1,0,1,0,0,0,1,1"/>
   <wangtile tileid="22" wangid="1,1,1,0,0,0,1,1"/>
   <wangtile tileid="23" wangid="1,0,1,0,1,0,1,0"/>
   <wangtile tileid="24" wangid="1,0,1,0,1,0,1,1"/>
   <wangtile tileid="25" wangid="1,1,1,0,1,0,1,1"/>
   <wangtile tileid="26" wangid="1,0,1,1,1,0,1,1"/>
   <wangtile tileid="27" wangid="1,1,1,0,1,1,1,1"/>
   <wangtile tileid="28" wangid="1,1,1,1,1,1,1,1"/>
  </wangset>
  <wangset name="Path" type="edge" tile="16">
   <wangcolor name="Path" color="#ff0000" tile="-1" probability="1"/>
   <wangtile tileid="15" wangid="1,0,0,0,0,0,0,0"/>
   <wangtile tileid="16" wangid="1,0,0,0,1,0,0,0"/>
   <wangtile tileid="17" wangid="1,0,0,0,0,0,1,0"/>
   <wangtile tileid="18" wangid="1,0,1,0,0,0,1,0"/>
   <wangtile tileid="19" wangid="1,0,1,0,1,0,1,0"/>
  </wangset>
 </wangsets>
</tileset>
