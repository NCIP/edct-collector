<?php

/**
 * Add new method toArray
 */
class MySimpleXMLElement extends SimpleXMLElement{
  // object to array, no comments
  public function toArray($obj){
    $arr = (array)$obj;
    if(empty($arr)){
      $arr = "";
    } else {
      foreach($arr as $key=>$value){
        //if($key === 'answer'/* && gettype($value) == 'MySimpleXMLElement'*/) {d(gettype($value));d($arr);}
        if(!is_scalar($value)){
          $arr[$key] = $this->toArray($value);
        }
      }
    }
    return $arr;
  }
}