(ns clj-pdf.test.core
  (:use clj-pdf.core clojure.test clojure.java.io)
  (:require [clojure.string :as re]))


(defn doc-to-str [doc]
  (let [out (new java.io.ByteArrayOutputStream)] 
    (write-doc doc out)
  (.toString out)))

(defn fix-pdf [pdf]
  (-> pdf
  (re/replace #"ModDate\((.*?)\)" "")
  (re/replace #"CreationDate\((.*?)\)" "")
  (re/replace #"\[(.*?)\]" "")))

(defn eq? [doc1 doc2]
  (is (= (fix-pdf doc1) (fix-pdf doc2))))


(deftest doc-meta-test []
  (eq?
       (doc-to-str [{:title  "Test doc"
                     :left-margin   10
                     :right-margin  50
                     :top-margin    20
                     :bottom-margin 25
                     :size          "a4"
                     :orientation   "landscape"
                     :subject "Some subject"
                     :author "John Doe"
                     :creator "Jane Doe"
                     :doc-header ["inspired by" "William Shakespeare"]
                     :header "page header"
                     :footer "page"}
                    [:chunk "meta test"]])
       "%PDF-1.4\n%����\n2 0 obj\n<</Length 83/Filter/FlateDecode>>stream\nx�3P0T�5T0P0�4�ɹ\\�\\N!\\�f\n���\n!)\\@Q ��f�`h��0P(J���M-IT(I-.�\f�\n��Ժ�pr Ck�\nendstream\nendobj\n4 0 obj\n<</Parent 3 0 R/Contents 2 0 R/Type/Page/Resources<</ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 1 0 R>>>>/MediaBox[0 0 595 842]/Rotate 90>>\nendobj\n1 0 obj\n<</BaseFont/Helvetica/Type/Font/Encoding/WinAnsiEncoding/Subtype/Type1>>\nendobj\n3 0 obj\n<</ITXT(2.1.7)/Type/Pages/Count 1/Kids[4 0 R]>>\nendobj\n5 0 obj\n<</Type/Catalog/Pages 3 0 R>>\nendobj\n6 0 obj\n<</Creator(Jane Doe)/Producer(iText 2.1.7 by 1T3XT)/inspired#20by(William Shakespeare)/Title(Test doc)/Subject(Some subject)/ModDate(D:20120419090904-04'00')/Author(John Doe)/CreationDate(D:20120419090904-04'00')>>\nendobj\nxref\n0 7\n0000000000 65535 f \n0000000331 00000 n \n0000000015 00000 n \n0000000419 00000 n \n0000000164 00000 n \n0000000482 00000 n \n0000000527 00000 n \ntrailer\n<</Root 5 0 R/ID [<706f5ddd48f46d042be979975b21ef90><58922c4cc5bdc60ab3b8f1f7850be5e0>]/Info 6 0 R/Size 7>>\nstartxref\n757\n%%EOF\n"))

