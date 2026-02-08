package services;

import models.AnnonceSortie;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnonceSortieService {

    public List<AnnonceSortie> getAll() {
        List<AnnonceSortie> list = new ArrayList<>();
        String sql = "SELECT * FROM annonce_sortie ORDER BY id DESC";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AnnonceSortie a = new AnnonceSortie();
                a.setId(rs.getInt("id"));

                // ✅ ta DB: user_id
                a.setCreatorId(rs.getInt("user_id"));

                a.setTitre(rs.getString("titre"));
                a.setDescription(rs.getString("description"));
                a.setVille(rs.getString("ville"));

                // ✅ ta DB: lieu_texte
                a.setLieu(rs.getString("lieu_texte"));

                a.setTypeActivite(rs.getString("type_activite"));

                Timestamp ts = rs.getTimestamp("date_sortie");
                a.setDateSortie(ts == null ? null : ts.toLocalDateTime());

                a.setBudgetMax(rs.getDouble("budget_max"));
                a.setNbPlaces(rs.getInt("nb_places"));

                // optionnel (si ton model ne l'a pas, ignore)
                // a.setImageUrl(rs.getString("image_url"));

                a.setStatut(rs.getString("statut"));
                list.add(a);
            }

        } catch (SQLException e) {
            System.err.println("❌ getAll SQL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void add(AnnonceSortie a) {
        // ✅ colonnes EXACTES de ta table
        String sql = """
            INSERT INTO annonce_sortie
            (user_id, titre, description, ville, lieu_texte, type_activite, date_sortie, budget_max, nb_places, image_url, statut)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, a.getCreatorId());          // on garde creatorId côté Java = user_id côté DB
            ps.setString(2, a.getTitre());
            ps.setString(3, a.getDescription());
            ps.setString(4, a.getVille());
            ps.setString(5, a.getLieu());            // lieu -> lieu_texte
            ps.setString(6, a.getTypeActivite());

            // date_sortie est NOT NULL => obligatoire
            ps.setTimestamp(7, Timestamp.valueOf(a.getDateSortie()));

            ps.setDouble(8, a.getBudgetMax());
            ps.setInt(9, a.getNbPlaces());

            // image_url nullable
            ps.setString(10, null);

            // enum => "OUVERTE" / "CLOTUREE" / "ANNULEE"
            ps.setString(11, a.getStatut());

            int rows = ps.executeUpdate();
            System.out.println("✅ add(): rows inserted = " + rows);

        } catch (SQLException e) {
            System.err.println("❌ add SQL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(AnnonceSortie a) {
        String sql = """
            UPDATE annonce_sortie SET
                user_id=?,
                titre=?,
                description=?,
                ville=?,
                lieu_texte=?,
                type_activite=?,
                date_sortie=?,
                budget_max=?,
                nb_places=?,
                statut=?
            WHERE id=?
        """;

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, a.getCreatorId());
            ps.setString(2, a.getTitre());
            ps.setString(3, a.getDescription());
            ps.setString(4, a.getVille());
            ps.setString(5, a.getLieu());
            ps.setString(6, a.getTypeActivite());
            ps.setTimestamp(7, Timestamp.valueOf(a.getDateSortie()));
            ps.setDouble(8, a.getBudgetMax());
            ps.setInt(9, a.getNbPlaces());
            ps.setString(10, a.getStatut());
            ps.setInt(11, a.getId());

            int rows = ps.executeUpdate();
            System.out.println("✅ update(): rows updated = " + rows);

        } catch (SQLException e) {
            System.err.println("❌ update SQL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM annonce_sortie WHERE id=?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println("✅ delete(): rows deleted = " + rows);

        } catch (SQLException e) {
            System.err.println("❌ delete SQL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}