<?php 

// Template for submit my questionniare block.
//d($_SERVER); die;
  $sname = 'https://' . $_SERVER['SERVER_NAME'] . '/';
  global $base_path;
?>
<?php if(empty($arg)): ?>
  <h3>You have no submitted quarterly report sections now.</h3>
<?php else: ?>
  <table><tbody>
  <?php foreach($arg as $module): ?>
    <tr>
      <td><?php echo $module['date']?></td>
      <td><b><?php echo $module['name']?></b></td>
      <td><?php echo l("View", 'files/module/' . $module['context'])?></td>
      <td><?php echo l('<img alt="Image" src="' . url(drupal_get_path('theme', 'acquia_marina') . '/images/printer.png') . '">',
                       'files/module/' . $module['context'], 
                       array('attributes' => array('target' => '_blank'), 'html' => true, 'query' => array('param'=>'print')))?>
      </td>
    </tr>
  <?php endforeach ?>
  </table></tbody>
<?php endif ?>
