# HelloWorld2
## 概要
Spring Bootを用いたwebで動作するHelloWorldです。名前を入力するとHello 〇〇!と表示されます。それだけでは面白くないので入力された苗字が全国で何番目なのかのランキングをThymeleafで表示する機能をつけました。

## 使用方法
### 環境構築
#### DBの作成
MySQL上に適当なデータベースを作成してMySQL Workbench上から以下のSQL文を順に実行してください。接続ポート番号は3306としています。
<ul>
  <li>CreateSchemaAndTable.sql</li>
  <li>InsertRegions.sql</li>
  <li>InsertRegionMaps.sql</li>
  <li>InsertFamilyNames.sql</li>
</ul>

#### 設定
アプリケーションプロパティファイル/src/main/resources/application.propertiesを開いて以下の項目に値を追加します。データベースにアクセスする権限のあるロールを持つユーザーを指定してください。
<ul>
  <li>spring.datasource.username</li>
  <li>spring.datasource.password</li>
</ul>

### 実行
#### プログラムの実行
<ol>
  <li>Gradleの以下のタスクを実行してください（IDEのメニューから実行するのが手っ取り早いです）。
    <ul>
      <li>--configure-on-demand -x check bootRun</li>
    </ul>
  </li>
  <li>ブラウザで<a href="http://localhost:8080/">http://localhost:8080/</a>にアクセスします。</li>
  <li>テキストフィールドに名前（苗字）を入力して送信ボタンを押します。</li>
</ol>

#### テストコードの実行
Gradleの以下のタスクを実行してください。
<ol>
  <li>--configure-on-demand --rerun-tasks -x check test</li>
</ol>

## その他
### 使用した環境
作成にあたって使用した環境です。
<table>
  <tr>
    <td>JDK</td>
    <td>OpenJDK 23</td>
  </tr>
  <tr>
    <td>Spring Boot</td>
    <td>3.4.0</td>
  </tr>
  <tr>
    <td>IDE</td>
    <td>Apache NetBeans 23</td>
  </tr>
  <tr>
    <td>DB</td>
    <td>MySQL 8.0</td>
  </tr>
</table>

### レイヤーについて
本来はController, Service, Repositoryの3層に分けて作るのが正しい流儀のようですが今回はServiceレイヤーは作りませんでした（作者が手抜きしたかったから…）。 

### 元データについて
苗字のランキングの元データはこちらです。<a href="https://myoji-yurai.net/prefectureRanking.htm">全国苗字ランキング</a>
