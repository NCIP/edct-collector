<?php

class RestCall {

  private $settings, $errors = array(), $url, $restcall;

  public function __construct(){
    $this->settings = caCure_get_settings();
    $this->url = $this->generateURL();
  }

  public function getRestCallUrl() {
    return $this->restcall;
  }

  private function setError($err){
    echo $err;
  }

  private function generateURL(){
    try{
      return 'http://' . $this->settings['server'] . ':' . $this->settings['port'] . '/' . $this->settings['context'] . '/api/';
    }catch(Exception $e){
      $this->setError($e);
    }
  }

  private function generateContext($data) {
    $context = array();
    if (is_array($data)) {
      ksort($data);
      $context['http'] = array(
        'method' => 'POST',
        'content' => http_build_query($data, '', '&'),
      );
    }
    return $context;
  }

  public function send($url, $data = null, $method = 'POST') {
    return drupal_http_request($url, array("text/xml"), $method, $data);
  }

  private function get(){
    return @file_get_contents($this->restcall);
  }

  private function prepareUrl($query, $identifier = true){
    global $user;

    if ($identifier === true) {
      $istr = $identifier ? $user->identifier . '/' : '';
    }
    else if (is_string($identifier)){
      $istr = $identifier.'/';
    }
    $this->url = $this->generateURL();
    $this->url .= $istr . $this->settings['api'][$query];
  }

  private function call($query, $data = array(), $flag = true){
    $this->clear();
    $this->prepareUrl($query, $flag);
    $data_str = '';
    if(!empty($data)){
      $data_str = $this->dataToStr($data);
    }
    $this->restcall = $this->url . $data_str;
    return $this->get();
  }

  private function dataToStr($data){
    $output = '?';
    $list = array();
    if(is_array($data)){
      while(list($key) = each($data)){
        $list[] = $key . '=' . $data[$key];
      }
      $output .= implode('&', $list);
    }
    return $output;
  }

  public function clear(){
    $this->restcall = '';
    $this->url = $this->generateURL();
  }

  /**
   * Methods to call
   */

  // Module metadata API

  public function allModules() {
    return $this->call(__FUNCTION__, array(), false);
  }

  public function getModuleStatusByOwner($module_id) {
    return $this->call(__FUNCTION__, array('moduleId' => $module_id), false);
  }

  public function getAllUserModules($context) {
    return $this->call(__FUNCTION__, array('ctx' => $context));
  }

  public function getAvaliableModules($context) {
    return $this->call(__FUNCTION__, array('ctx' => $context));
  }

  public function getCurrentModule() {
    return $this->call(__FUNCTION__, array(), false);
  }

  public function changeModuleStatus($id, $status) {
    return $this->call(__FUNCTION__, array('id' => $id, 'status' => $status));
  }

  public function getStaleForms() {
    return $this->call(__FUNCTION__, array(), false);
  }

  public function getNextFormId($id) {
    return $this->call(__FUNCTION__,array('id' => $id));
  }

  public function getPrevFormId($id) {
    return $this->call(__FUNCTION__, array('id' => $id));
  }

  // Form Data APIs

  public function getForm($id) {
    return $this->call(__FUNCTION__, array('id' => $id));
  }

  public function getFormData($id, $format = 'XML') {
    $format = strtoupper($format);
    if ($format != 'JSON' || $format != 'XML') {
      $format = 'XML';
    }
    return $this->call(__FUNCTION__, array('id' => $id, 'format' => $format), false);
  }

  public function saveForm($id, $partial_save = true) {
    $data = $GLOBALS['HTTP_RAW_POST_DATA'];
    $this->prepareUrl(__FUNCTION__);
    $params = array();
    $params['id'] = $id;
    $params['partialSave'] = $partial_save;
    $url = $this->url  . $this->dataToStr($params);
    return $this->send($url, $data);
  }

  public function changeFormStatus($id, $status) {
    return $this->call(__FUNCTION__, array('id' => $id, 'status' => $status));
  }

  // Access and Security APIs

  public function getAllSharingGroups() {
    return $this->call(__FUNCTION__, array(), false);
  }

  /**
   * Method creates new entity in group which is specified by identificator.
   * Method returns identificator of new entity.
   * @param (array) $data
   * @return (string)
   */
  public function getNewEntityInGroup($grid) {
    return $this->call(__FUNCTION__, array('grid' => $grid), false);
  }

  public function getNewEntityInNewGroup($group_name ){
    return $this->call(__FUNCTION__, array('name' => $group_name), false);
  }

  public function assignEntityToGroup($grpid) {
    return $this->call(__FUNCTION__, array('grpid' => $grpid));
  }

  public function getGroupId($name) {
    return $this->call(__FUNCTION__, array('name' => $name), false);
  }

  public function getPermissions() {
    return $this->call(__FUNCTION__, array(), false);
  }

  public function getPermissionsForEntity() {
    return $this->call(__FUNCTION__);
  }

  public function savePermissions($eid, $xml) {
    $this->prepareUrl(__FUNCTION__, $eid);
    return $this->send($this->url, $xml);
  }

  public function getTags() {
    return $this->call(__FUNCTION__, array(), false);
  }
}