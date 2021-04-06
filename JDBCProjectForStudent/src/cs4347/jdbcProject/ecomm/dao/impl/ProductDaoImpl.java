/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of every team member to the Provost Office for academic 
 * dishonesty. 
 */

package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO {

	private static final String insertSQL = "INSERT INTO product (prodName, prodDescription, prodCategory, prodUPC) VALUES (?, ?, ?, ?);";

	final static String selectQuery = "SELECT id, prodName, prodDescription, prodCategory, prodUPC "
			+ "FROM product where id = ?";

	final static String updateSQL = "UPDATE product SET prodName = ?, prodDescription = ?, prodCategory = ?, prodUPC = ? "
			+ "WHERE id = ?;";

	final static String deleteSQL = "DELETE FROM product WHERE id = ?;";

	@Override
	public Product create(Connection connection, Product product) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		if (product.getId() != null) {
			throw new DAOException("Trying to insert Product with NON-NULL ID");
		}

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());

			ps.executeUpdate();

			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			product.setId((long) lastKey);

			return product;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}

	}

	@Override
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		if (id == null) {

			throw new DAOException("Trying to retrieve Product with NULL ID");

		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Product product = new Product();
			product.setId(rs.getLong("id"));
			product.setProdName(rs.getString("prodName"));
			product.setProdDescription(rs.getString("prodDescription"));
			product.setProdCategory(rs.getInt("prodCategory"));
			product.setProdUPC(rs.getString("prodUPC"));
			;

			return product;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}

	}

	@Override
	public int update(Connection connection, Product product) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		if (product.getId() == null) {
			throw new DAOException("Trying to update Product with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
			ps.setLong(5, product.getId());

			int rows = ps.executeUpdate();
			return rows;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}

	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		if (id == null) {
			throw new DAOException("Trying to delete Product with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rows = ps.executeUpdate();
			return rows;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}

	}

	final static String storeQuery = "SELECT id, prodName, prodDescription, prodCategory, prodUPC "
			+ "FROM product where prodCategory = ?";

	@Override
	public List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(storeQuery);
			ps.setInt(1, category);
			ResultSet rs = ps.executeQuery();

			List<Product> result = new ArrayList<Product>();
			while (rs.next()) {
				Product prod = new Product();
				prod.setId(rs.getLong("id"));
				prod.setProdName(rs.getString("prodName"));
				prod.setProdDescription(rs.getString("prodDescription"));
				prod.setProdCategory(rs.getInt("prodCategory"));
				prod.setProdUPC(rs.getString("prodUPC"));

				result.add(prod);
			}
			return result;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}

	}

	final static String selectUPC = "SELECT id, prodName, prodDescription, prodCategory, prodUPC "
			+ "FROM product where prodUPC = ?";

	@Override
	public Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException {

		if (upc == null) {

			throw new DAOException("Trying to retrieve Product with NULL UPC");

		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectUPC);
			ps.setString(1, upc);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Product product = new Product();
			product.setId(rs.getLong("id"));
			product.setProdName(rs.getString("prodName"));
			product.setProdDescription(rs.getString("prodDescription"));
			product.setProdCategory(rs.getInt("prodCategory"));
			product.setProdUPC(rs.getString("prodUPC"));
			;

			return product;

		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

}
